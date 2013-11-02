package com.sebamawa.acalculadora;

import com.example.acalculadora.R;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity implements OnClickListener{
	
	//constantes para los Tags (comandos) de los botones
	public static final String  COMANDO_NUMERO = "NUMERO",
					            COMANDO_SUMAR = "SUMAR",
					            COMANDO_RESTAR = "RESTAR",
					            COMANDO_MULTIPLICAR = "MULTIPLICAR",
					            COMANDO_DIVIDIR = "DIVIDIR",
					            COMANDO_IGUAL = "IGUAL",
					            COMANDO_LIMPIAR = "LIMPIAR",
								COMANDO_CAMBIAR_SIGNO = "CAMBIAR_SIGNO";
	//enumerado para las operaciones
	public static enum Operacion{SUMA, RESTA, PRODUCTO, DIVISION, NINGUNA};
	
	//Views
	private Button btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9,
	        btnMas, btnMenos, btnPor, btnDiv, btnIgual, btnLimpiar, btnMas_Menos;
	EditText etCampoNumerico;
	
	private Calculadora_Logica calc;  //objeto para la lógica de la calculadora
	                                  //(operaciones)
	private boolean ultimoPresionadoNumero;
	private boolean ultimoPresionadoOperacion;
	
	//====================================================================//

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
			
		//obtenemos referencias a los componentes (views)
		ubicarComponentes();
		
		//establece TAGs para los botones (para su posterior manejo
		//en OnClick())
		establecerTags();
		
		//adiciona escucha de eventos a los botones (implementados por esta clase)
		asignarEscuchasBotones();
		
		//el campo de texto inicia con el número 0
		etCampoNumerico.setText("0");
		
		calc = new Calculadora_Logica();  //instancia para la lógica de la calculadora
	}
	
	//=======================================================================//
	
	private void ubicarComponentes(){
		btn0 = (Button) findViewById(R.id.btn0);
		btn1 = (Button) findViewById(R.id.btn1);
		btn2 = (Button) findViewById(R.id.btn2);
		btn3 = (Button) findViewById(R.id.btn3);
		btn4 = (Button) findViewById(R.id.btn4);
		btn5 = (Button) findViewById(R.id.btn5);
		btn6 = (Button) findViewById(R.id.btn6);
		btn7 = (Button) findViewById(R.id.btn7);
		btn8 = (Button) findViewById(R.id.btn8);
		btn9 = (Button) findViewById(R.id.btn9);
		btnMas = (Button) findViewById(R.id.btnMas);
		btnMenos = (Button) findViewById(R.id.btnMenos);
		btnPor = (Button) findViewById(R.id.btnPor);
		btnDiv = (Button) findViewById(R.id.btnDiv);
		btnIgual = (Button) findViewById(R.id.btnIgual);
		btnLimpiar = (Button) findViewById(R.id.btnLimpiar);
		btnMas_Menos = (Button) findViewById(R.id.btnMas_Menos);
		
		etCampoNumerico = (EditText) findViewById(R.id.etCampoNumerico);
	}
	
	//=========================================================================//
	
	private void establecerTags(){
		btn0.setTag(COMANDO_NUMERO);
		btn1.setTag(COMANDO_NUMERO);
		btn2.setTag(COMANDO_NUMERO);
		btn3.setTag(COMANDO_NUMERO);
		btn4.setTag(COMANDO_NUMERO);
		btn5.setTag(COMANDO_NUMERO);
		btn6.setTag(COMANDO_NUMERO);
		btn7.setTag(COMANDO_NUMERO);
		btn8.setTag(COMANDO_NUMERO);
		btn9.setTag(COMANDO_NUMERO);
		btnDiv.setTag(COMANDO_DIVIDIR);
		btnIgual.setTag(COMANDO_IGUAL);
		btnLimpiar.setTag(COMANDO_LIMPIAR);
		btnMas.setTag(COMANDO_SUMAR);
		btnMenos.setTag(COMANDO_RESTAR);
		btnPor.setTag(COMANDO_MULTIPLICAR);
		btnMas_Menos.setTag(COMANDO_CAMBIAR_SIGNO);
	}
	
	//=======================================================================//
	
	private void asignarEscuchasBotones(){
		btn0.setOnClickListener(this);
		btn1.setOnClickListener(this);
		btn2.setOnClickListener(this);
		btn3.setOnClickListener(this);
		btn4.setOnClickListener(this);
		btn5.setOnClickListener(this);
		btn6.setOnClickListener(this);
		btn7.setOnClickListener(this);
		btn8.setOnClickListener(this);
		btn9.setOnClickListener(this);
		btnDiv.setOnClickListener(this);
		btnIgual.setOnClickListener(this);
		btnLimpiar.setOnClickListener(this);
		btnMas.setOnClickListener(this);
		btnMenos.setOnClickListener(this);
		btnPor.setOnClickListener(this);
		btnMas_Menos.setOnClickListener(this);
	}
	
	//=======================================================================//

	//acción al presionar un botón (implementa interfaz de escucha OnClickListener())
	@Override
	public void onClick(View v) {
		//Introducción de un número "n". Si hay un cero en el campo lo sustituimos 
		//por n, sino lo cancatenamos con el número (distinto de cero) que hay en el campo
		//en el caso de que lo último presionado fue un botón de número.
		//Si lo último presionado fue operación escribimos n en el campo.
		if ((String)v.getTag() == COMANDO_NUMERO){
			Button b = (Button)v;
			String numero = (String) b.getText();
			if (ultimoPresionadoNumero){
				if (etCampoNumerico.getText().equals("0")){
					etCampoNumerico.setText(numero);
				}else{
					etCampoNumerico.setText(etCampoNumerico.getText() + numero);
				}
			}else{
				etCampoNumerico.setText(numero);
			}
			
			ultimoPresionadoNumero = true;
			ultimoPresionadoOperacion = false;
		}else 
			if ((String)v.getTag() == COMANDO_CAMBIAR_SIGNO){
				//si el número en el EditText inicia con el signo "-"
				//cambiamos su signo
				if (!etCampoNumerico.getText().toString().equalsIgnoreCase("0")){
					if (etCampoNumerico.getText().toString().charAt(0) == '-'){
						etCampoNumerico.setText(etCampoNumerico.getText().toString().substring(1));
					}else{  //o concatenamos "-" al principio del número
						etCampoNumerico.setText("-"+etCampoNumerico.getText().toString());
					}
				}
			}else
				if ((String)v.getTag() == COMANDO_IGUAL && ultimoPresionadoNumero && 
					calc.getOperacion() != Calculadora_Logica.Operacion.NINGUNA){
						this.etCampoNumerico.setText(""+calc.realizarOperacion(
								Integer.parseInt(this.etCampoNumerico.getText().toString())));
				}else
					if ((String)v.getTag() == COMANDO_SUMAR){
						if (calc.getOperacion() == Calculadora_Logica.Operacion.NINGUNA || 
								ultimoPresionadoOperacion){
							calc.setOperacion(Calculadora_Logica.Operacion.SUMA, 
									Integer.parseInt(this.etCampoNumerico.getText().toString()));
						}else{
							calc.realizarOperacion(Integer.parseInt(this.etCampoNumerico.getText().toString()));
							
							calc.setOperacion(Calculadora_Logica.Operacion.SUMA, calc.getResultado());
							this.etCampoNumerico.setText(""+calc.getResultado());
						}
						ultimoPresionadoNumero = false;
						ultimoPresionadoOperacion = true;
					}else
						if ((String)v.getTag() == COMANDO_RESTAR){
							if (calc.getOperacion() == Calculadora_Logica.Operacion.NINGUNA || 
									ultimoPresionadoOperacion){
								calc.setOperacion(Calculadora_Logica.Operacion.RESTA, 
										Integer.parseInt(this.etCampoNumerico.getText().toString()));
							}else{
								calc.realizarOperacion(Integer.parseInt(this.etCampoNumerico.getText().toString()));
								
								calc.setOperacion(Calculadora_Logica.Operacion.RESTA, calc.getResultado());
								this.etCampoNumerico.setText(""+calc.getResultado());
							}
							ultimoPresionadoNumero = false;
							ultimoPresionadoOperacion = true;
						}else
							if ((String)v.getTag() == COMANDO_MULTIPLICAR){
								if (calc.getOperacion() == Calculadora_Logica.Operacion.NINGUNA || 
										ultimoPresionadoOperacion){
									calc.setOperacion(Calculadora_Logica.Operacion.PRODUCTO, 
											Integer.parseInt(this.etCampoNumerico.getText().toString()));
								}else{
									calc.realizarOperacion(Integer.parseInt(this.etCampoNumerico.getText().toString()));
									
									calc.setOperacion(Calculadora_Logica.Operacion.PRODUCTO, calc.getResultado());
									this.etCampoNumerico.setText(""+calc.getResultado());
								}
								ultimoPresionadoNumero = false;
								ultimoPresionadoOperacion = true;
							}else
								if ((String)v.getTag() == COMANDO_DIVIDIR){
									if (calc.getOperacion() == Calculadora_Logica.Operacion.NINGUNA ||
											ultimoPresionadoOperacion){
										calc.setOperacion(Calculadora_Logica.Operacion.DIVISION, 
												Integer.parseInt(this.etCampoNumerico.getText().toString()));
									}else{
										calc.realizarOperacion(Integer.parseInt(this.etCampoNumerico.getText().toString()));
										
										calc.setOperacion(Calculadora_Logica.Operacion.DIVISION, calc.getResultado());
										this.etCampoNumerico.setText(""+calc.getResultado());
									}
									ultimoPresionadoNumero = false;
									ultimoPresionadoOperacion = true;
								}else
									if ((String)v.getTag() == COMANDO_LIMPIAR){
										calc.reiniciar();
										this.etCampoNumerico.setText("0");
										ultimoPresionadoNumero = false;
									}
		
		/*
		//se debería anidar (if else if) pues switch(String) no está permitido para Android
		//Se dejan los if separados por claridad
		if ((String)v.getTag() == COMANDO_DIVIDIR){
			if (calc.getOperacion() == Calculadora_Logica.Operacion.NINGUNA){
				calc.setOperacion(Calculadora_Logica.Operacion.DIVISION, 
						Integer.parseInt(this.etCampoNumerico.getText().toString()));
			}else{
				calc.realizarOperacion(Integer.parseInt(this.etCampoNumerico.getText().toString()));
				
				calc.setOperacion(Calculadora_Logica.Operacion.DIVISION, calc.getResultado());
				this.etCampoNumerico.setText(""+calc.getResultado());
			}
			ultimoPresionadoNumero = false;
		}  //del if (DIVIDIR)
		
		if ((String)v.getTag() == COMANDO_MULTIPLICAR){
			if (calc.getOperacion() == Calculadora_Logica.Operacion.NINGUNA){
				calc.setOperacion(Calculadora_Logica.Operacion.PRODUCTO, 
						Integer.parseInt(this.etCampoNumerico.getText().toString()));
			}else{
				calc.realizarOperacion(Integer.parseInt(this.etCampoNumerico.getText().toString()));
				
				calc.setOperacion(Calculadora_Logica.Operacion.PRODUCTO, calc.getResultado());
				this.etCampoNumerico.setText(""+calc.getResultado());
			}
			ultimoPresionadoNumero = false;
		}  //del if (MULTIPLICAR)
		
		if ((String)v.getTag() == COMANDO_SUMAR){
			if (calc.getOperacion() == Calculadora_Logica.Operacion.NINGUNA){
				calc.setOperacion(Calculadora_Logica.Operacion.SUMA, 
						Integer.parseInt(this.etCampoNumerico.getText().toString()));
			}else{
				calc.realizarOperacion(Integer.parseInt(this.etCampoNumerico.getText().toString()));
				
				calc.setOperacion(Calculadora_Logica.Operacion.SUMA, calc.getResultado());
				this.etCampoNumerico.setText(""+calc.getResultado());
			}
			ultimoPresionadoNumero = false;
		}  //del if (SUMAR)
		
		if ((String)v.getTag() == COMANDO_RESTAR){
			if (calc.getOperacion() == Calculadora_Logica.Operacion.NINGUNA){
				calc.setOperacion(Calculadora_Logica.Operacion.RESTA, 
						Integer.parseInt(this.etCampoNumerico.getText().toString()));
			}else{
				calc.realizarOperacion(Integer.parseInt(this.etCampoNumerico.getText().toString()));
				
				calc.setOperacion(Calculadora_Logica.Operacion.RESTA, calc.getResultado());
				this.etCampoNumerico.setText(""+calc.getResultado());
			}
			ultimoPresionadoNumero = false;
		}  //del if (DIVIDIR)	
		
		if ((String)v.getTag() == COMANDO_IGUAL){
			this.etCampoNumerico.setText(""+calc.realizarOperacion(
					Integer.parseInt(this.etCampoNumerico.getText().toString())));
		}
		
		if ((String)v.getTag() == COMANDO_LIMPIAR){
			calc.reiniciar();
			this.etCampoNumerico.setText("0");
			ultimoPresionadoNumero = false;
		}
		
		if ((String)v.getTag() == COMANDO_NUMERO){
			Button b = (Button)v;
			String numero = (String) b.getText();
			if (ultimoPresionadoNumero){
				if (etCampoNumerico.getText().equals("0")){
					etCampoNumerico.setText(numero);
				}else{
					etCampoNumerico.setText(etCampoNumerico.getText() + numero);
				}
			}else{
				etCampoNumerico.setText(numero);
			}
			
			ultimoPresionadoNumero = true;
		}	
		
		if ((String)v.getTag() == COMANDO_CAMBIAR_SIGNO){
			//si el número en el EditText inicia con el signo "-"
			//cambiamos su signo
			if (!etCampoNumerico.getText().equals("0")){
				if (etCampoNumerico.getText().toString().charAt(0) == '-'){
					etCampoNumerico.setText(etCampoNumerico.getText().toString().substring(1));
				}else{
					etCampoNumerico.setText("-"+etCampoNumerico.getText().toString());
				}
			}
		}
	*/	
	}  //de onClick
	
}  //de la clase MainActivity
