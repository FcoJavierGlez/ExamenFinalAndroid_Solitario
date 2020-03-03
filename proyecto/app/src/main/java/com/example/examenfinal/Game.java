package com.example.examenfinal;

import java.util.ArrayList;

public class Game {

    private static int numJugadas = 0;
    int tablero [][] = {
            {1,1,0},
            {1,1,1},
            {1,1,0},
            {1,1,1}
    };
    private static ArrayList<String> listaMovimientos = new ArrayList<String>();

    private Coordenada fichaSeleccionada = null;
    private Coordenada casillaLibreSeleccionada = null;

    final static int NCOLUMNAS = 3;
    final static int NFILAS = 4;

    Coordenada coordenadasValidas[][] = null;


    /* MÉTODOS */

    public Game() {}

    public void reiniciarPartida() {
        reiniciaTablero();
        numJugadas = 0;
        listaMovimientos.clear();
    }

    private void reiniciaTablero() {
        tablero[0][0] = tablero[1][0] = tablero[2][0] = tablero[3][0] = 1;
        tablero[0][1] = tablero[1][1] = tablero[2][1] = tablero[3][1] = 1;
        tablero[0][2] = tablero[2][2] = 0;
        tablero[1][2] = tablero[3][2] = 1;
    }

    public void setFicha(Coordenada coordenada) {
        if (tablero[coordenada.getFila()][coordenada.getColumna()]!=1)
            return;
        fichaSeleccionada = coordenada;
    }

    public void setCasillaLibre(Coordenada coordenada) throws MovimientoInvalido {
        if (tablero[coordenada.getFila()][coordenada.getColumna()]!=0 ||
                !( Math.abs(fichaSeleccionada.getFila()-coordenada.getFila())==2 || Math.abs(fichaSeleccionada.getColumna()-coordenada.getColumna())==2 ) ||
            !validaMovimiento(coordenada))
            throw new MovimientoInvalido();
        casillaLibreSeleccionada = coordenada;
    }

    private boolean validaMovimiento(Coordenada coordenada) {
        if (fichaSeleccionada.getFila()==coordenada.getFila()){
            if (fichaSeleccionada.getColumna()>coordenada.getColumna())
                return (tablero[coordenada.getFila()][coordenada.getColumna()+1]==1);
            else
                return (tablero[coordenada.getFila()][coordenada.getColumna()-1]==1);
        } else if (fichaSeleccionada.getColumna()==coordenada.getColumna()) {
            if (fichaSeleccionada.getFila()>coordenada.getFila())
                return (tablero[coordenada.getFila()+1][coordenada.getColumna()]==1);
            else
                return (tablero[coordenada.getFila()-1][coordenada.getColumna()]==1);
        }
        return false;
    }

    public void realizaJugada() {
        if (fichaSeleccionada.getFila()==casillaLibreSeleccionada.getFila()){
            if (fichaSeleccionada.getColumna()>casillaLibreSeleccionada.getColumna())
                tablero[casillaLibreSeleccionada.getFila()][casillaLibreSeleccionada.getColumna()+1] = 0;
            else
                tablero[casillaLibreSeleccionada.getFila()][casillaLibreSeleccionada.getColumna()-1] = 0;
        } else {
            if (fichaSeleccionada.getFila()>casillaLibreSeleccionada.getFila())
                tablero[casillaLibreSeleccionada.getFila()+1][casillaLibreSeleccionada.getColumna()] = 0;
            else
                tablero[casillaLibreSeleccionada.getFila()-1][casillaLibreSeleccionada.getColumna()] = 0;
        }
        tablero[fichaSeleccionada.getFila()][fichaSeleccionada.getColumna()] = 0;
        tablero[casillaLibreSeleccionada.getFila()][casillaLibreSeleccionada.getColumna()] = 1;
        fichaSeleccionada = null;
        casillaLibreSeleccionada = null;
    }

    public Coordenada getFichaSeleccionada() {
        return fichaSeleccionada;
    }

    public Coordenada getCasillaLibreSeleccionada() {
        return casillaLibreSeleccionada;
    }

    public void incrementaNumJugada() {
        numJugadas++;
    }

    public void reseteaFichaSeleccionada() {
        fichaSeleccionada=null;
    }

    public int getNumJugadas() {
        return numJugadas;
    }

    public boolean juegoGanado() {
        int contador = 0;
        for (int i=0; i<NFILAS; i++)
            for (int j=0; j<NCOLUMNAS; j++)
                contador += tablero[i][j];
        return (contador==1);
    }

    /*public boolean juegoPerdido() {
        for (int i=0; i<NFILAS; i++)
            for (int j=0; j<NCOLUMNAS; j++)
                if (tablero[i][j]==1) ;
    }*/

}
