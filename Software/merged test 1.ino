#include <WiFi.h>
#include <WiFiClient.h>
#include <WiFiServer.h>
#include <WiFiUdp.h>



#include <Stepper.h>

#define STEPS 100





int previous = 0;
int led1 = 2;
int led2 = 3;
int led3 = 4;
int led4 = 5;
int led5 = 6;
int led6 = 7;
int led7 = 8;
void setup() {
  
 // stepper.setSpeed(250);
  pinMode(led1, OUTPUT);
  pinMode(led2, OUTPUT);
  pinMode(led3, OUTPUT);
  pinMode(led4, OUTPUT);
  pinMode(led5, OUTPUT);
  pinMode(led6, OUTPUT);
  pinMode(led7, OUTPUT);
  
}

void loop() {

  int val = analogRead(0);


 // stepper.step(val - previous);

  
  previous = val;

  digitalWrite(led1, HIGH); //Green LED



  digitalWrite(led2, HIGH); ///red led


  digitalWrite(led3, HIGH);
 

  digitalWrite(led4, HIGH);

  digitalWrite(led5, HIGH);
 

  digitalWrite(led6, HIGH);


  digitalWrite(led7, HIGH);
  



}








