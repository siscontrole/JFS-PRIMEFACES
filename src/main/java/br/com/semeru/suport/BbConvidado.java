package br.com.semeru.suport;

import br.com.semeru.model.dao.HibernateDAO;
import br.com.semeru.model.dao.InterfaceDAO;
import br.com.semeru.model.entities.Convidado;
import br.com.semeru.model.entities.Evento;
import br.com.semeru.util.FacesContextUtil;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;


@ManagedBean(name="bbConvidado")
@RequestScoped
public class BbConvidado implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private List<Convidado> convidado;

    
    public List<Convidado> getConvidados(){
        InterfaceDAO<Convidado> convidadoDAO = new HibernateDAO<Convidado>(Convidado.class, FacesContextUtil.getRequestSession()); 
        return convidadoDAO.getEntities();
    }    
    
     
    
}
