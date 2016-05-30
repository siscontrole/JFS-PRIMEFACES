package br.com.semeru.model.entities;


import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.Where;


@Entity(name = "JoinConvidadoEventoPessoa")
@Table (name="convidado")
public class Convidado implements Serializable{
    private static final long serialVersionUID =  1L;
    
    @Id
    @GeneratedValue
    @Column(name="idConvidado", nullable=false)
    private Integer idConvidado;
    @Column (name="nome", nullable = false, length = 80 )
    private String nome;
    @Column (name="email", nullable = false, length = 80 )
    private String email;    
    @Column (name="dataDeCadastro", nullable = false,  columnDefinition="TIMESTAMP default CURRENT_TIMESTAMP")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date dataDeCadastro= new Date();    
    @Column (name="numConvAdultos", nullable = true, columnDefinition="tinyint(2) default 0" )
    private Integer numConvAdultos;    
    @Column (name="numConvCriancas", nullable = true, columnDefinition="tinyint(2) default 0" )
    private Integer numConvCriancas;      
    @Column (name="url", nullable = true, length = 255, columnDefinition="varchar(255) default 'n/d'")
    private String url;          
    @Column (name="detalhesDoConvidado", nullable = true, length = 255 )
    private String detalhesDoConvidado;       
    @Column (name="confirmado", nullable = false)
    private Integer confirmacao;          
    @Column (name="estado", nullable = false)
    private Integer estado; 
    
    @Column (name="disparado", nullable = true, columnDefinition="tinyint(2) default 0")
    private Integer disparado;     
    
    @Column (name="disparos", nullable = true, columnDefinition="tinyint(2) default 0")
    private Integer disparos;  
    
    private Integer selecao; 

    public Integer getSelecao() {
        return selecao;
    }

    public void setSelecao(Integer selecao) {
        this.selecao = selecao;
    }
    
    

    public Integer getDisparado() {
        return disparado;
    }

    public void setDisparado(Integer disparado) {
        this.disparado = disparado;
    }

    public Integer getDisparos() {
        return disparos;
    }

    public void setDisparos(Integer disparos) {
        this.disparos = disparos;
    }
    
    
    
    
    @ManyToOne(optional=false)
    @ForeignKey(name = "ConvidadoEventoFK") 
    @JoinColumn(name="idEvento", referencedColumnName = "idEvento")
    public Evento evento;    
    private String situacaoConversao;  
    private String confirmacaoConversao; 
    

    public String getSituacaoConversao() {
        String retorno;
        if(this.estado==1){
            retorno = "Ativo";
        }else{
            retorno = "Inativo";            
        }
        
      return retorno;        
    }
    
    public String getConfirmacaoConversao() {
        String retorno = "";
        if(this.confirmacao==1){
            retorno = "Falta responder";
        }
        if(this.confirmacao==2){
            retorno = "Vai";
        }
        if(this.confirmacao==3){
            retorno = "Não Vai";
        }
        
      return retorno;        
    }
    
    
    public Long getId() {  
        return new Long(idConvidado);  
    }  

    public String getdetalhesDoConvidado() {
        return detalhesDoConvidado;
    }

    public void setDetales(String detalhesDoConvidado) {
        this.detalhesDoConvidado = detalhesDoConvidado;
    }

    public Convidado() {
    }

    public Integer getIdConvidado() {
        return idConvidado;
    }

    public void setIdConvidado(Integer idConvidado) {
        this.idConvidado = idConvidado;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDataDeCadastro() {
        return dataDeCadastro;
    }

    public void setDataDeCadastro(Date dataDeCadastro) {
        this.dataDeCadastro = dataDeCadastro;
    }

    public Integer getNumConvAdultos() {
        return numConvAdultos;
    }

    public void setNumConvAdultos(Integer numConvAdultos) {
        this.numConvAdultos = numConvAdultos;
    }

    public Integer getNumConvCriancas() {
        return numConvCriancas;
    }

    public void setNumConvCriancas(Integer numConvCriancas) {
        this.numConvCriancas = numConvCriancas;
    }

    public String getDetalhesDoConvidado() {
        return detalhesDoConvidado;
    }

    public void setDetalhesDoConvidado(String detalhesDoConvidado) {
        this.detalhesDoConvidado = detalhesDoConvidado;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public Integer getConfirmacao() {
        return confirmacao;
    }

    public void setConfirmacao(Integer confirmacao) {
        this.confirmacao = confirmacao;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }    

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + Objects.hashCode(this.idConvidado);
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
        final Convidado other = (Convidado) obj;
        if (!Objects.equals(this.idConvidado, other.idConvidado)) {
            return false;
        }
        return true;
    }


    
}
