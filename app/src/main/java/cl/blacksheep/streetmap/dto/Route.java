package cl.blacksheep.streetmap.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by elsan on 07-04-2018.
 */

public class Route {

    public static boolean ESPECIAL = false;
    public static String NOMBRE = "route";
    private String routeId;
    private String agencyId;
    private String routeShortName;
    private String routeLongName;
    private String routeType;
    private String routeColor;
    private String routeTextColor;
    private List<Shape> shapes = new ArrayList();
    private String routeSequence;

    public static int TIPO = 1;
    public static String ESTADO = "i";
    public static String SENTIDO_IDA = "i";
    public static String SENTIDO_VUELTA = "r";

    public static double NORTE = 0.0;
    public static double NORESTE = 1.0;
    public static double ESTE = 2.0;
    public static double SURESTE = 3.0;
    public static double SUR = 4.0;
    public static double SUROESTE = 5.0;
    public static double OESTE = 6.0;
    public static double NOROESTE = 7.0;



    public String getRouteId() {
        return routeId;
    }

    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }

    public String getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(String agencyId) {
        this.agencyId = agencyId;
    }

    public String getRouteShortName() {
        return routeShortName;
    }

    public void setRouteShortName(String routeShortName) {
        this.routeShortName = routeShortName;
    }

    public String getRouteLongName() {
        return routeLongName;
    }

    public void setRouteLongName(String routeLongName) {
        this.routeLongName = routeLongName;
    }

    public String getRouteType() {
        return routeType;
    }

    public void setRouteType(String routeType) {
        this.routeType = routeType;
    }

    public String getRouteColor() {
        return routeColor;
    }

    public void setRouteColor(String routeColor) {
        this.routeColor = routeColor;
    }

    public String getRouteTextColor() {
        return routeTextColor;
    }

    public void setRouteTextColor(String routeTextColor) {
        this.routeTextColor = routeTextColor;
    }

    public List<Shape> getShapes() {
        return shapes;
    }

    public void setShapes(List<Shape> shapes) {
        this.shapes = shapes;
    }

    public String getRouteSequence() {
        return routeSequence;
    }

    public void setRouteSequence(String routeSequence) {
        this.routeSequence = routeSequence;
    }



}
