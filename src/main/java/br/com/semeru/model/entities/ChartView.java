package br.com.semeru.model.entities;
 
import br.com.semeru.util.FacesContextUtil;
import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.hibernate.transform.Transformers;
import org.primefaces.model.chart.PieChartModel;
import org.springframework.security.core.context.SecurityContextHolder;
 
@ManagedBean(name = "bbChartView")
public class ChartView implements Serializable {
 
    private PieChartModel pieModel1;
    private PieChartModel pieModel2;
    private PieChartModel pieModel3;
    
    public Integer getConfirmacoesFalta() {        
        List<Convidado> convidados = FacesContextUtil.getRequestSession().createCriteria(Convidado.class)
                .createAlias("evento", "e")
                .add(Restrictions.eq("e.pessoa.idPessoa", getIdUsuario()))
                .add(Restrictions.eq("confirmacao", 1))
                .list();
        Integer count = convidados.size();        
        System.out.println("Sem resposta: " + String.valueOf(count));  
        return count;
    }  
    
    public Integer getConfirmacoesNaoIrao() {        
        List<Convidado> convidados = FacesContextUtil.getRequestSession().createCriteria(Convidado.class)
                .createAlias("evento", "e")
                .add(Restrictions.eq("e.pessoa.idPessoa", getIdUsuario()))
                .add(Restrictions.eq("confirmacao", 3))
                .list();
        Integer count = convidados.size();                
        return count;
    }  

    public Integer getConfirmacoesIrao() {        
        List<Convidado> convidados = FacesContextUtil.getRequestSession().createCriteria(Convidado.class)
                .createAlias("evento", "e")
                .add(Restrictions.eq("e.pessoa.idPessoa", getIdUsuario()))
                .add(Restrictions.eq("confirmacao", 2))
                .list();
        Integer count = convidados.size();                
        return count;
    }    
    
    public Integer getNumeroAdultos() {         
        List<Convidado> convidados = FacesContextUtil.getRequestSession().createCriteria(Convidado.class)
                .createAlias("evento", "e")
                .add(Restrictions.eq("e.pessoa.idPessoa", getIdUsuario()))
                .add(Restrictions.eq("confirmacao", 2))
                .list();
        Integer count = convidados.size();                
        return count;
    }  
    
    public Integer getNumeroCriancas() {        
        List<Convidado> convidados = FacesContextUtil.getRequestSession().createCriteria(Convidado.class)
                .createAlias("evento", "e")
                .add(Restrictions.eq("e.pessoa.idPessoa", getIdUsuario()))
                .add(Restrictions.gt("numConvCriancas", 0))
                .list();
        Integer count = convidados.size();                
        return count;
    }    

    public Integer getIdUsuario() {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        Pessoa pessoaCod = (Pessoa) FacesContextUtil.getRequestSession().createCriteria(Pessoa.class)
                .add(Restrictions.eq("login", login))
                .uniqueResult();
        return pessoaCod.idPessoa;
    } 
    
    @PostConstruct
    public void init() {
        createPieModels();
    }
 
    public PieChartModel getPieModel1() {
        return pieModel1;
    }
    
    public PieChartModel getPieModel3() {
        return pieModel3;
    }      
    
    public PieChartModel getPieModel2() {
        return pieModel2;
    }
     
    private void createPieModels() {
        createPieModel1();
        createPieModel2();
        createPieModel3();
    }
 
    private void createPieModel1() {
        Integer faltamResponder = getConfirmacoesFalta();
        Integer Irao = getConfirmacoesIrao();
        Integer naoIrao = getConfirmacoesNaoIrao();
        pieModel1 = new PieChartModel();
        pieModel1.set("Faltam responder", faltamResponder);
        pieModel1.set("Irão", Irao);
        pieModel1.set("Não irão", naoIrao);
        pieModel1.setSeriesColors("FFC11E,6ca200,cb5b40");
        pieModel1.setTitle("Confirmações");
        pieModel1.setLegendPosition("s");
    }
    
    private void createPieModel2() {
        Integer numeroAdultos = getNumeroAdultos();
        Integer numeroCriancas = getNumeroCriancas();
        
        
        pieModel2 = new PieChartModel();
        pieModel2.set("Adultos", numeroAdultos);
        pieModel2.set("Crianças", numeroCriancas); 
        pieModel2.setSeriesColors("6ca200,367200");         
        pieModel2.setTitle("Distribuição");
        pieModel2.setLegendPosition("s");
    }   
    
    private void createPieModel3() {
        pieModel3 = new PieChartModel();
        pieModel3.set("Ativos", 325);
        pieModel3.set("Desativados", 540);        
        pieModel3.setSeriesColors("c9474c,ff6c7e"); 
        pieModel3.setTitle("Situação");
        pieModel3.setLegendPosition("s");
        
    }     
}