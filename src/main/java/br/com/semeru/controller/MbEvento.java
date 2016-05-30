package br.com.semeru.controller;

import br.com.semeru.model.dao.HibernateDAO;
import br.com.semeru.model.dao.InterfaceDAO;
import br.com.semeru.model.entities.Evento;
import br.com.semeru.model.entities.Pessoa;
import br.com.semeru.util.FacesContextUtil;
import java.io.Serializable;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.hibernate.criterion.Restrictions;
import org.springframework.security.core.context.SecurityContextHolder;

@ManagedBean(name = "mbEvento")
@ViewScoped
public class MbEvento implements Serializable {

    public static final long serialVersionUID = 1L;

    private Evento evento = new Evento();
    private Pessoa pessoa = new Pessoa();
    private List<Evento> eventos;    
    private List<Pessoa> pessoas;
    
    public void addMessage(String summary, String detail) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }    
    
    public Integer getIdUsuario() {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();

        Pessoa pessoaCod = (Pessoa) FacesContextUtil.getRequestSession().createCriteria(Pessoa.class)
                .add(Restrictions.eq("login", login))
                .uniqueResult();

        return pessoaCod.idPessoa;
    }    
       
    public List<Evento> getEventos() {
        eventos = FacesContextUtil.getRequestSession().createCriteria(Evento.class)
                .createAlias("pessoa", "p")
                .add(Restrictions.eq("p.idPessoa", getIdUsuario()))
                .list();
        return eventos;
    }
    
    public List<Pessoa> getPessoas() {
        return pessoas;
    }
    
    

    public void setPessoas(List<Pessoa> pessoas) {
        this.pessoas = pessoas;
    }
    
    

    public MbEvento() { 
    }

    
    private InterfaceDAO<Pessoa> pessoaDAO() {
        InterfaceDAO<Pessoa> pessoaDAO = new HibernateDAO<Pessoa>(Pessoa.class, FacesContextUtil.getRequestSession());
        return pessoaDAO;
    }
    
    private InterfaceDAO<Evento> eventoDAO() {
        InterfaceDAO<Evento> eventoDAO = new HibernateDAO<Evento>(Evento.class, FacesContextUtil.getRequestSession());
        return eventoDAO;
    }    

    

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) { 
        this.pessoa = pessoa;
    }

    public String limpEvento() {
        evento = new Evento();
        return editEvento();
    }
    
    public String cancelarEvento() {
        evento = new Evento();
        return "";
    }

    public String editEvento() {
        return "/restrict/cadastrarevento.faces";
    }

    public String addEvento() {
        if (evento.getIdEvento() == null || evento.getIdEvento() == 0) {
            insertEvento();
        } else {
            updateEvento();
        }
        limpEvento();
        return null;
    }

    private void insertEvento() {
        eventoDAO().save(evento);
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Gravação efetuada com sucesso", ""));
    }

    private void updateEvento() {
        eventoDAO().update(evento);
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Atualização efetuada com sucesso", ""));
    }

    public void deleteEvento() {
        eventoDAO().remove(evento);
    }



    public void setEvento(List<Evento> eventos) {
        this.eventos = eventos;
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }
}
