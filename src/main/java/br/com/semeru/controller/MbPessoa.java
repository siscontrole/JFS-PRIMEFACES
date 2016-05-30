package br.com.semeru.controller;

import br.com.semeru.conversores.ConverterSHA1;
import br.com.semeru.model.dao.HibernateDAO;
import br.com.semeru.model.dao.InterfaceDAO;
import br.com.semeru.model.entities.EmailMensagem;
import br.com.semeru.model.entities.Empresa;

import br.com.semeru.model.entities.Pessoa;
import br.com.semeru.util.FacesContextUtil;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.hibernate.criterion.Restrictions;

@ManagedBean
@SessionScoped
public class MbPessoa implements Serializable {

    private static final long serialVersionUID = 1L;
    private String confereSenha;
    private Pessoa pessoa = new Pessoa();
    private String senhaAntiga;
    
    private List<Pessoa> pessoas;
    

    public MbPessoa() {
    }

public void enviarEmailDados()  {
        Empresa empresa = (Empresa) FacesContextUtil.getRequestSession().createCriteria(Empresa.class).uniqueResult();
        EmailMensagem mensagem = (EmailMensagem) FacesContextUtil.getRequestSession().createCriteria(EmailMensagem.class)
                .add(Restrictions.eq("tipo", 1))
                .uniqueResult();
        
        HtmlEmail email = new HtmlEmail();
        email.setHostName(empresa.getSmtp());
        email.setSmtpPort(Integer.valueOf(empresa.getPorta()) );
        try {
                String msgHtml = mensagem.getMensagem();
                String nomeUsuario = this.pessoa.getNome();
                String usuarioUsuario = this.pessoa.getLogin();
                String senhaUsuario = this.pessoa.getSenha();
                String link = "<a href='#'>link do painel</a>";

                msgHtml = msgHtml.replace("[NOME]", nomeUsuario);
                msgHtml = msgHtml.replace("[USUARIO]", usuarioUsuario);
                msgHtml = msgHtml.replace("[SENHA]", senhaUsuario);                
                msgHtml = msgHtml.replace("[LINK_CONFIRMACAO]", link);
                email.addTo(this.pessoa.getEmail(), this.pessoa.getNome());
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
    
    private InterfaceDAO<Pessoa> pessoaDAO() {
        InterfaceDAO<Pessoa> pessoaDAO = new HibernateDAO<Pessoa>(Pessoa.class, FacesContextUtil.getRequestSession());
        return pessoaDAO;
    }

    
    public String limpPessoa() {
        senhaAntiga = pessoa.getSenha(); 
        pessoa = new Pessoa();
        return editPessoa();
    }    
    
    public String cancelarPessoa() {        
        pessoa = new Pessoa();
        return "";
    }     

    public String editPessoa() {        
        return "/restrict/cadastrarpessoa.faces";
    }

    public String addPessoa() {
        
        if (pessoa.getIdPessoa() == null || pessoa.getIdPessoa() == 0) {
            Date date = new Date();
            pessoa.setDataDeCadastro(date);            
            insertPessoa();
        } else {            
            updatePessoa();
        }
        limpPessoa();
        return null;
    }

    private void insertPessoa() {         
        pessoa.setSenha(ConverterSHA1.cipher(pessoa.getSenha()));
        if (pessoa.getSenha() == null ? confereSenha == null : pessoa.getSenha().equals(ConverterSHA1.cipher(confereSenha))) {
            //pessoa.setPermissao("ROLE_ADMIN");
            pessoaDAO().save(pessoa);            
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Gravação efetuada com sucesso", ""));
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "As senhas não conferem.", ""));
        }
    }

    private void updatePessoa() {        
        System.out.println("XPTO: senha: "+senhaAntiga);
        
        Boolean continua = true;
        
        if(!confereSenha.trim().isEmpty()){                     
            pessoa.setSenha(ConverterSHA1.cipher(pessoa.getSenha()));
            
            if (pessoa.getSenha() == null ? confereSenha == null : pessoa.getSenha().equals(ConverterSHA1.cipher(confereSenha))) {                
                continua = true;
            } else {
                FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "As senhas não conferem.", ""));
                continua = false;
            }
        }
        
        if(continua){
            pessoaDAO().update(pessoa);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Atualização efetuada com sucesso", ""));
        }
    }

    public String deletePessoa() {
        pessoaDAO().remove(pessoa);
        
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro excluído com sucesso", ""));
        return null;
    }

    public List<Pessoa> getPessoas() {
        pessoas = pessoaDAO().getEntities();
        return pessoas;
    }

    public void setPessoas(List<Pessoa> pessoas) {
        this.pessoas = pessoas;
    }

    

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    

    

    public String getConfereSenha() {
        return confereSenha;
    }

    public void setConfereSenha(String confereSenha) {
        this.confereSenha = confereSenha;
    }
}
