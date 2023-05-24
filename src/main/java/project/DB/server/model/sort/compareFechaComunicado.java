package project.DB.server.model.sort;

import java.util.Comparator;

import project.DB.server.model.Comunicados;

public class compareFechaComunicado implements Comparator<Comunicados> {

    @Override
    public int compare(Comunicados o1, Comunicados o2) {
        return o2.getFechaInicio().compareTo(o1.getFechaInicio());
    }
    
}
