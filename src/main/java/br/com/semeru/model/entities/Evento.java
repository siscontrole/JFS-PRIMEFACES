
package br.com.semeru.model.entities;

import br.com.semeru.util.FacesContextUtil;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import org.hibernate.annotations.ForeignKey;
import org.hibernate.criterion.Restrictions;

@Entity
@Table(name="evento")
public class Evento implements Serializable, BaseEntity{
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue
    @Column(name = "idEvento", nullable= false)
    private Integer idEvento;
    @Column(name="nome", length=255, nullable=false)
    private String nome;
    
    @ManyToOne(optional=false)
    @ForeignKey(name = "EventoPessoaFK")    
    @JoinColumn(name="idPessoa", referencedColumnName = "idPessoa")    
    private Pessoa pessoa;   
    
   // @ForeignKey(name = "EventoConvidadoFK") 
    //@JoinColumn(name="idEvento", referencedColumnName = "idEvento")
    //private Evento evento;
    

    
    @Column (name="dataDoEvento", nullable = true)
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataDoEvento;    
    
    @Column (name="horaDoEvento", length=5, nullable = true)    
    private String horaDoEvento;    
    
    @Column (name="localDoEvento", length=255, nullable = true)    
    public String localDoEvento;    
    
    @Column (name="detalhesDoEvento", length=255, nullable = true)    
    private String detalhesDoEvento;    

    public Evento pesquisarPorCodigo(Integer codigo){        
       Evento eventoPorCod = (Evento) FacesContextUtil.getRequestSession().createCriteria(Evento.class)
        .add( Restrictions.eq("idEvento", codigo) )    
        .uniqueResult();        
        return eventoPorCod;        
    }

  

    public String getDetalhesDoEvento() {
        return detalhesDoEvento;
    }

    public void setDetalhesDoEvento(String detalhesDoEvento) {
        this.detalhesDoEvento = detalhesDoEvento;
    }

    public String getLocalDoEvento() {
        return localDoEvento;
    }

    public void setLocalDoEvento(String localDoEvento) {
        this.localDoEvento = localDoEvento;
    }

    public String getHoraDoEvento() {
        return horaDoEvento;
    }

    public void setHoraDoEvento(String horaDoEvento) {
        this.horaDoEvento = horaDoEvento;
    }    

    public Date getDataDoEvento() {
        return dataDoEvento;
    }

    public void setDataDoEvento(Date dataDoEvento) {
        this.dataDoEvento = dataDoEvento;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public Evento() {
        this.pessoa = new Pessoa();
    }

    public Integer getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(Integer idEvento) {
        this.idEvento = idEvento;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.idEvento);
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
        final Evento other = (Evento) obj;
        if (!Objects.equals(this.idEvento, other.idEvento)) {
            return false;
        }
        return true;
    }
    
    @Override
    public Integer pegarId(){
        return idEvento;
    }  
    
    
}
