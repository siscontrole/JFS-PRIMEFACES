package br.com.semeru.model.entities;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="email")
public class EmailMensagem implements Serializable{
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue
    @Column(name = "idEmail", nullable= false)
    private Integer idEmail;    
    
    @Column(name="assunto", length=255, nullable=false)
    private String assunto;         
    
    @Column(name="mensagem", columnDefinition="TEXT")
    private String mensagem;      
    
    @Column (name="estado", nullable = false)
    private Integer estado; 
    
    @Column (name="tipo", nullable = true, columnDefinition="tinyint(2) default 0")
    private Integer tipo;     

    public Integer getTipo() {
        return tipo;
    }

    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }

    
    public EmailMensagem(Integer idEmail) {
        this.idEmail = idEmail;
    }

    public EmailMensagem() {
    }
    
    

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + Objects.hashCode(this.idEmail);
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
        final EmailMensagem other = (EmailMensagem) obj;
        if (!Objects.equals(this.idEmail, other.idEmail)) {
            return false;
        }
        return true;
    }

    public Integer getIdEmail() {
        return idEmail;
    }

    public void setIdEmail(Integer idEmail) {
        this.idEmail = idEmail;
    }

    public String getAssunto() {
        return assunto;
    }

    public void setAssunto(String assunto) {
        this.assunto = assunto;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    
    
}
