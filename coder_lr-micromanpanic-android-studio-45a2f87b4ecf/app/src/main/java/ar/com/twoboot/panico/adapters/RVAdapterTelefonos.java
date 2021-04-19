package ar.com.twoboot.panico.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import ar.com.twoboot.panico.R;

public class RVAdapterTelefonos extends RecyclerView.Adapter<RVAdapterTelefonos.TelefonoViewHolder> {
    private ArrayList<String> nombres;
    private Context c;

    public RVAdapterTelefonos(ArrayList<String> nombres, Context c) {
        this.nombres = nombres;
        this.c = c;
    }

    @Override
    public int getItemCount() {
        return nombres.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public TelefonoViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_telefonos, viewGroup, false);
        TelefonoViewHolder tvh = new TelefonoViewHolder(v);
        return tvh;
    }

    @Override
    public void onBindViewHolder(TelefonoViewHolder telViewHolder, int i) {
        telViewHolder.nombre.setText(nombres.get(i));
        if (nombres.get(i).equals("Bomberos")) {
            telViewHolder.nombre.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    bomberos();
                }
            });
            telViewHolder.cv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    bomberos();
                }
            });
            telViewHolder.icono.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    bomberos();
                }
            });
        }
        if (nombres.get(i).equals("Policia")) {
            telViewHolder.nombre.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    policia();
                }
            });
            telViewHolder.cv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    policia();
                }
            });
            telViewHolder.icono.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    policia();
                }
            });
        }
        if (nombres.get(i).equals("Asistencia al Niño")) {
            telViewHolder.nombre.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    aninio();
                }
            });
            telViewHolder.icono.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    aninio();
                }
            });
            telViewHolder.cv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    aninio();
                }
            });
        }
        if (nombres.get(i).equals("Hospital Dr. Enrique Vera Barros")) {
            telViewHolder.nombre.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    vbarros();
                }
            });
            telViewHolder.cv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    vbarros();
                }
            });
            telViewHolder.icono.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    vbarros();
                }
            });
        }
        if (nombres.get(i).equals("Emergencia Medica")) {
            telViewHolder.nombre.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    emedica();
                }
            });
            telViewHolder.icono.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    emedica();
                }
            });
            telViewHolder.cv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    emedica();
                }
            });
        }
        if (nombres.get(i).equals("Violencia de Genero")) {
            telViewHolder.nombre.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    violencia();
                }
            });
            telViewHolder.cv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    violencia();
                }
            });
            telViewHolder.icono.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    violencia();
                }
            });
        }
    }

    public static class TelefonoViewHolder extends RecyclerView.ViewHolder {
        TextView nombre;
        CardView cv;
        ImageView icono;

        TelefonoViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);
            nombre = (TextView) itemView.findViewById(R.id.telefonos_nombre);
            icono = (ImageView) itemView.findViewById(R.id.icono_img);
        }
    }

    public void bomberos() {
        llamar("100");
    }

    public void policia() {
        llamar("911");
    }

    public void aninio() {
        llamar("102");
    }

    public void vbarros() {
        llamar("08004440211");
    }

    public void emedica() {
        llamar("107");
    }

    public void violencia() {
        llamar("4439312");
    }

    @SuppressLint("MissingPermission")
    private void llamar(String numero) {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + numero));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        c.startActivity(intent);
    }

}
