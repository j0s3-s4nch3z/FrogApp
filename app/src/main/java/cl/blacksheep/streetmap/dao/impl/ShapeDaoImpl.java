package cl.blacksheep.streetmap.dao.impl;

import java.io.InputStream;
import java.util.List;

import cl.blacksheep.streetmap.dao.ShapeDao;
import cl.blacksheep.streetmap.dto.Shape;
import cl.blacksheep.streetmap.utils.Utilidades;

/**
 * Created by elsan on 07-04-2018.
 */

public class ShapeDaoImpl implements ShapeDao{

    @Override
    public List<Shape> getShape(InputStream is) {
        Utilidades.leerRuta2(is);
        return Utilidades.SHAPES;
    }
}
