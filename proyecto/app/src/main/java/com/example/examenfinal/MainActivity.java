package com.example.examenfinal;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private final int [][] ids = {
            {R.id.f0c0,R.id.f0c1,R.id.f0c2},
            {R.id.f1c0,R.id.f1c1,R.id.f1c2},
            {R.id.f2c0,R.id.f2c1,R.id.f2c2},
            {R.id.f3c0,R.id.f3c1,R.id.f3c2},
    };

    Game game = new Game();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView display = (TextView)findViewById(R.id.display);

        final Button botonReset = findViewById(R.id.reset);

        botonReset.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                game.reiniciarPartida();
                display.setText(String.valueOf(game.getNumJugadas()));
                pintaTablero();
            }
        });
    }

    public void pulsado(View v){
        final TextView display = (TextView)findViewById(R.id.display);
        int fila, columna, id = v.getId();
        ImageButton imageButton = (ImageButton) v;

        if (!game.juegoGanado()) {
            if (game.getFichaSeleccionada()==null)
                game.setFicha(coorJuego(id));
            else {
                try {
                    game.setCasillaLibre((coorJuego(id)));
                    game.realizaJugada();
                    game.incrementaNumJugada();
                    display.setText(String.valueOf(game.getNumJugadas()));
                } catch (MovimientoInvalido movimientoInvalido) {
                    Toast.makeText(getApplicationContext(), "Movimiento inválido", Toast.LENGTH_SHORT).show();
                    game.reseteaFichaSeleccionada();
                }
            }
            pintaTablero();
            if (game.juegoGanado()) Toast.makeText(getApplicationContext(), "¡Enhorabuena has ganado!", Toast.LENGTH_SHORT).show();
        }


    }

    private void pintaTablero() {
        for (int i=0; i<game.NFILAS; i++)
            for (int j=0; j<game.NCOLUMNAS; j++)
                if (game.tablero[i][j]==1)
                    dibujarCasilla(coorFicha(ids[i][j]), 1);
                else if (game.tablero[i][j]==0)
                    dibujarCasilla(coorFicha(ids[i][j]), 0);
    }

    private void dibujarCasilla(Coordenada coordenada, int num) {
        int fila = coordenada.getFila();
        int columna = coordenada.getColumna();

        int id = ids[fila][columna];
        ImageButton imageButton = (ImageButton) findViewById(id);

        if(num == 1) imageButton.setImageResource(R.drawable.ficha);
        else imageButton.setImageResource(R.drawable.fondo);
    }

    private Coordenada coorJuego(int id) {
        int columna = 0;
        int fila = 0;

        for (int i=0; i < Game.NFILAS; i++)
            for (int j=0; j < Game.NCOLUMNAS; j++)
                if (ids[i][j] == id) {
                    columna = j;
                    fila = i;
                }

        //Coordenada coordenada = new Coordenada( game.seleccionarFilas(columna),  columna);
        Coordenada coordenada = new Coordenada( fila,  columna);
        return coordenada;
    }

    private Coordenada coorFicha(int id) {
        int columna = 0;
        int fila = 0;

        for (int i=0; i < Game.NFILAS; i++)
            for (int j=0; j < Game.NCOLUMNAS; j++)
                if (ids[i][j] == id) {
                    columna = j;
                    fila = i;
                }

        Coordenada coordenada = new Coordenada( fila,  columna);
        return coordenada;
    }




}
