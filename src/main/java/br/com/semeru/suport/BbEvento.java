package br.com.semeru.suport;

import br.com.semeru.model.entities.Evento;
import br.com.semeru.model.entities.Pessoa;
import br.com.semeru.util.FacesContextUtil;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import org.hibernate.criterion.Restrictions;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;


@ManagedBean(name="bbEvento")
@RequestScoped
public class BbEvento implements Serializable {
    private static final long serialVersionUID = 1L;
    
    
    public List<Evento> getEventos(){
        Pessoa usuario = new Pessoa();
        SecurityContext context = SecurityContextHolder.getContext();
        
        if(context instanceof SecurityContext){
            Authentication authentication = context.getAuthentication();
            if(authentication instanceof Authentication){
                usuario.setLogin(((User) authentication.getPrincipal()).getUsername());
            }
        }
        
      return FacesContextUtil.getRequestSession().createCriteria(Evento.class)
      .add( Restrictions.eq("idPessoa", new Integer(usuario.getIdPessoa())))
      .list();  
    }    
    
}
