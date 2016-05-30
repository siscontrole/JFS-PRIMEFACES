
package br.com.semeru.conversores;

import br.com.semeru.model.entities.Evento;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(forClass=Evento.class)
public class ConversorEvento implements Converter{
    
    @Override
    public Object getAsObject(FacesContext arg0, UIComponent arg1, String codigoString) {
            if(codigoString != null && codigoString.trim().length()>0){
                Integer codigo = Integer.valueOf(codigoString);  
                Evento evento = new Evento();
                return evento.pesquisarPorCodigo(codigo);                
            }
        return null;
    }

    @Override
    public String getAsString(FacesContext arg0, UIComponent arg1, Object value) {
            Evento evento = (Evento) value;
            return String.valueOf(evento.pegarId());            
    }
}