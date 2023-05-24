package project.DB.server.model.sort;

import java.util.Comparator;

import project.DB.server.model.Consumos;

public class compareFechaConsumos implements Comparator<Consumos> {

    @Override
    public int compare(Consumos o1, Consumos o2) {
        return o2.getFecha().compareTo(o1.getFecha());
    }
    
}
