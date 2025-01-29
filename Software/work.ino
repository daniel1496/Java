#include <Wire.h>
#include <WiFi.h>
#include <WiFiClient.h>
#include <WiFiServer.h>
#include <WiFiUdp.h>
#include <Stepper.h>

#define STEPS 100


Stepper stepper(STEPS, 8, 9, 10, 11);


int previous = 0;
int led1 = 2;
int led2 = 3;
int led3 = 4;
int led4 = 5;
int x = 0;
void setup() {
  // Start the I2C Bus as Master
  Wire.begin(); 
 stepper.setSpeed(250);
  pinMode(led1, OUTPUT);
  pinMode(led2, OUTPUT);
  pinMode(led3, OUTPUT);
  pinMode(led4, OUTPUT);
  
}
void loop() {
  Wire.beginTransmission(9); // transmit to device #9
  Wire.write(x);              // sends x 
  Wire.endTransmission();    // stop transmitting
  x++; // Increment x
  if (x > 5) x = 0; // `reset x once it gets 6
  delay(500);
  int val = analogRead(0);


  stepper.step(val - previous);

  
  previous = val;

  digitalWrite(led1, HIGH); //Green LED


  digitalWrite(led3, HIGH); ///red led
  delay (300);
  digitalWrite(led3, LOW);
  delay (300);

  digitalWrite(led2, HIGH);
  delay(100);
  digitalWrite(led2, LOW);
  delay(100);

  digitalWrite(led4, HIGH);
  delay (100);
  digitalWrite(led4, LOW);
  delay (100);


  
}

