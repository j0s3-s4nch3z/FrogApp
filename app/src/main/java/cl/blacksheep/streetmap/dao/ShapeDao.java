package cl.blacksheep.streetmap.dao;

import java.io.InputStream;
import java.util.List;

import cl.blacksheep.streetmap.dto.Shape;

/**
 * Created by elsan on 07-04-2018.
 */

public interface ShapeDao {

    public List<Shape> getShape(InputStream is);

}
