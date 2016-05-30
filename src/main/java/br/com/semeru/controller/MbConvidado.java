package br.com.semeru.controller;

import br.com.semeru.model.dao.HibernateDAO;
import br.com.semeru.model.dao.InterfaceDAO;
import br.com.semeru.model.entities.Convidado;
import br.com.semeru.model.entities.EmailMensagem;
import br.com.semeru.model.entities.Empresa;
import br.com.semeru.model.entities.Evento;
import br.com.semeru.model.entities.Pessoa;
import br.com.semeru.util.FacesContextUtil;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.SimpleEmail;

import org.hibernate.criterion.Restrictions;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.springframework.security.core.context.SecurityContextHolder;

@ManagedBean
@SessionScoped
public class MbConvidado implements Serializable {

    public static final long serialVersionUID = 1L;

    private Convidado convidado = new Convidado();
    private List<Convidado> convidados;
    private Evento evento = new Evento();
    private List<Evento> eventos;
    public Integer idUsuario;
    
    public void confirmaRemocao() {
        deleteConvidado();
        addMessage("Processo concluído", "O convidado foi removido.");
    }

    public void confirmaEnvio() throws EmailException {
        System.out.println("--- PROCESSAR ---");        
        enviarEmailsConvidados();
        addMessage("Processo concluído", "Envio concluído.");
    }
    
    public void confirmaEnvioIndividual() throws EmailException {
        System.out.println("--- PROCESSAR ---");        
        enviarEmailConvidado();
        addMessage("Processo concluído", "Envio concluído.");
    }    

    public void enviarEmailsConvidados()  {
        Empresa empresa = (Empresa) FacesContextUtil.getRequestSession().createCriteria(Empresa.class).uniqueResult();
        EmailMensagem mensagem = (EmailMensagem) FacesContextUtil.getRequestSession().createCriteria(EmailMensagem.class)
                .add(Restrictions.eq("tipo", 2))
                .uniqueResult();        

        for (int i = 0; i < this.convidados.size(); i++) {
            Convidado lista = convidados.get(i);            
            
            Evento evento = (Evento) FacesContextUtil.getRequestSession().createCriteria(Evento.class)
                .add(Restrictions.eq("evento",lista.evento.getIdEvento()))
                .uniqueResult();            
            
            System.out.println("--- disparando " + lista.getEmail() + "---");
            
            if(lista.getEstado().equals(1) &&  lista.getConfirmacao().equals(1)){
                HtmlEmail email = new HtmlEmail();
                email.setHostName(empresa.getSmtp());
                email.setSmtpPort(Integer.valueOf(empresa.getPorta()) );
                try {
                        String msgHtml = mensagem.getMensagem();
                        String nomeConvidado = lista.getNome();
                        String nomeEvento = lista.evento.getNome();
                        String local = lista.evento.getLocalDoEvento();
                        String hora = lista.evento.getHoraDoEvento().toString();
                        String data = lista.evento.getDataDoEvento().toString();
                        String link = "<a href='#'>link de confirmação</a>";

                        msgHtml = msgHtml.replace("[NOME]", nomeConvidado);
                        msgHtml = msgHtml.replace("[EVENTO_NOME]", nomeEvento);
                        msgHtml = msgHtml.replace("[EVENTO_LOCAL]", local);
                        msgHtml = msgHtml.replace("[EVENTO_DATA_HORA]", data + "-" + hora);
                        msgHtml = msgHtml.replace("[LINK_CONFIRMACAO]", link);

                        email.addTo(lista.getEmail(), lista.getNome());
                        email.setFrom(empresa.getEmail(), empresa.getNome());                
                        email.setHtmlMsg(msgHtml);
                        email.setSubject(mensagem.getAssunto());  
                        email.setSSL(true);
                        email.setAuthentication(empresa.getUsuario(), empresa.getSenha());                
                        email.send();
                } catch (EmailException ex) {
                    Logger.getLogger(MbConvidado.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
public void enviarEmailConvidado()  {
        Empresa empresa = (Empresa) FacesContextUtil.getRequestSession().createCriteria(Empresa.class).uniqueResult();
        EmailMensagem mensagem = (EmailMensagem) FacesContextUtil.getRequestSession().createCriteria(EmailMensagem.class)
                .add(Restrictions.eq("tipo", 2))
                .uniqueResult();
        
        HtmlEmail email = new HtmlEmail();
        email.setHostName(empresa.getSmtp());
        email.setSmtpPort(Integer.valueOf(empresa.getPorta()) );
        try {
                String msgHtml = mensagem.getMensagem();
                String nomeConvidado = this.convidado.getNome();
                String nomeEvento = this.convidado.evento.getNome();
                String local = this.convidado.evento.getLocalDoEvento();
                String hora = this.convidado.evento.getHoraDoEvento().toString();
                String data = this.convidado.evento.getDataDoEvento().toString();
                String link = "<a href='#'>link de confirmação</a>";

                msgHtml = msgHtml.replace("[NOME]", nomeConvidado);
                msgHtml = msgHtml.replace("[EVENTO_NOME]", nomeEvento);
                msgHtml = msgHtml.replace("[EVENTO_LOCAL]", local);
                msgHtml = msgHtml.replace("[EVENTO_DATA_HORA]", data + "-" + hora);
                msgHtml = msgHtml.replace("[LINK_CONFIRMACAO]", link);
                System.out.println("--- disparando " + nomeConvidado + "---");
                System.out.println("--- disparando " + this.convidado.getEmail() + "---");
                email.addTo(this.convidado.getEmail(), this.convidado.getNome());
                email.setFrom(empresa.getEmail(), empresa.getNome());                
                email.setHtmlMsg(msgHtml);
                email.setSubject(mensagem.getAssunto());  
                email.setSSL(true);
                email.setAuthentication(empresa.getUsuario(), empresa.getSenha());                
                email.send();
        } catch (EmailException ex) {
            Logger.getLogger(MbConvidado.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    

    

    public void addMessage(String summary, String detail) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Integer getIdUsuario() {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();

        Pessoa pessoaCod = (Pessoa) FacesContextUtil.getRequestSession().createCriteria(Pessoa.class)
                .add(Restrictions.eq("login", login))
                .uniqueResult();

        return pessoaCod.idPessoa;
    }

    public Convidado getConvidado() {
        return convidado;
    }

    public void setConvidado(Convidado convidado) {
        this.convidado = convidado;
    }

    public List<Convidado> getConvidados() {
        //System.out.println("O Login do cara é: " + login);               
        convidados = FacesContextUtil.getRequestSession().createCriteria(Convidado.class)
                .createAlias("evento", "e")
                .add(Restrictions.eq("e.pessoa.idPessoa", getIdUsuario()))
                .list();
        return convidados;
    }

    public void setConvidados(List<Convidado> convidados) {
        this.convidados = convidados;
    }

    private InterfaceDAO<Evento> eventoDAO() {
        InterfaceDAO<Evento> eventoDAO = new HibernateDAO<Evento>(Evento.class, FacesContextUtil.getRequestSession());
        return eventoDAO;
    }

    private InterfaceDAO<Convidado> convidadoDAO() {
        InterfaceDAO<Convidado> convidadoDAO = new HibernateDAO<Convidado>(Convidado.class, FacesContextUtil.getRequestSession());
        return convidadoDAO;
    }

    public String cancelarConvidado() {
        convidado = new Convidado();
        return "";
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public List<Evento> getEventos() {
        return eventos;
    }

    public void setEventos(List<Evento> eventos) {
        this.eventos = eventos;
    }

    public MbConvidado() {
        
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + Objects.hashCode(this.convidado);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final MbConvidado other = (MbConvidado) obj;
        if (!Objects.equals(this.convidado, other.convidado)) {
            return false;
        }
        return true;
    }

    public String limpConvidado() {
        convidado = new Convidado();
        return editConvidado();
    }

    public String editConvidado() {
        return "/restrict/cadastrarconvidado.faces";
    }

    public String addConvidado() {
        if (convidado.getIdConvidado() == null || convidado.getIdConvidado() == 0) {
            insertConvidado();
        } else {
            updateConvidado();
        }
        limpConvidado();
        return null;
    }

    private void insertConvidado() {
        convidadoDAO().save(convidado);
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Gravação efetuada com sucesso", ""));
    }

    private void updateConvidado() {
        convidadoDAO().update(convidado);
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Atualização efetuada com sucesso", ""));
    }

    public void deleteConvidado() {
        convidadoDAO().remove(convidado);
    }

}
