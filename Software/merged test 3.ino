#include <SoftwareSerial.h>
#include <SerialESP8266wifi.h>
#include <Servo.h>


#define sw_serial_rx_pin 0 //  Connect this pin to TX on the esp8266
#define sw_serial_tx_pin 1 //  Connect this pin to RX on the esp8266
#define esp8266_reset_pin 11 // Connect this pin to CH_PD on the esp8266, not reset. (let reset be unconnected)

SoftwareSerial swSerial(sw_serial_rx_pin, sw_serial_tx_pin);
Servo servo;
// the last parameter sets the local echo option for the ESP8266 module..
SerialESP8266wifi wifi(swSerial, swSerial, esp8266_reset_pin, Serial);//adding Serial enabled local echo and wifi debug
int pos = 0;
int led1 = 2;
int led2 = 3;
int led3 = 4;
int led4 = 5;
int led5 = 6;
int led6 = 7;
int led7 = 8;
String inputString;
boolean stringComplete = false;
unsigned long nextPing = 0;
;
void setup() {
  servo.attach(10);
  inputString.reserve(20);
  swSerial.begin(9600);
  Serial.begin(9600);
  while (!Serial)
    ;
  Serial.println("Starting wifi");

  wifi.setTransportToTCP();// this is also default
  // wifi.setTransportToUDP();//Will use UDP when connecting to server, default is TCP

  wifi.endSendWithNewline(true); // Will end all transmissions with a newline and carrage return ie println.. default is true

  wifi.begin();

  //Turn on local ap and server (TCP)
  wifi.startLocalAPAndServer("MY_CONFIG_AP", "", "localhost", "11739");

  wifi.connectToAP("EE-tkx6pj", "steam-itch-two");
  wifi.connectToServer("192.168.1.150", "8080");
  wifi.send(SERVER, "ESP8266 test app started");

  servo.attach(10);
  pinMode(led1, OUTPUT);
  pinMode(led2, OUTPUT);
  pinMode(led3, OUTPUT);
  pinMode(led4, OUTPUT);
  pinMode(led5, OUTPUT);
  pinMode(led6, OUTPUT);
  pinMode(led7, OUTPUT);
}

void loop() {

    digitalWrite(led1, HIGH); //Green LED
  delay(100);
  digitalWrite(led1, LOW);
  delay(100);

   digitalWrite(led2, HIGH); ///red led
 delay(100);
  digitalWrite(led2, LOW);
  delay(100);

  digitalWrite(led3, HIGH);
  delay(100);
  digitalWrite(led3, LOW);
  delay(100);

  digitalWrite(led4, HIGH);
   delay(100);
  digitalWrite(led4, LOW);
  delay(100);
  
  digitalWrite(led5, HIGH);
  delay(100);
  digitalWrite(led5, LOW);
  delay(100);

  digitalWrite(led6, HIGH);
 delay(100);
  digitalWrite(led6, LOW);
  delay(100);

  digitalWrite(led7, HIGH);
   delay(100);
  digitalWrite(led7, LOW);
  delay(100); 
    
    for (pos = 0; pos <= 210; pos += 5) { 
    // in steps of 1 degree
    servo.write(pos);              
    delay(15);                       
  }
  for (pos = 210; pos >= 0; pos -= 5) {
    servo.write(pos);              
    delay(15);                       
  }
  
  
  
  
  //Make sure the esp8266 is started..
  if (!wifi.isStarted())
    wifi.begin();

  
  //Listen for incoming messages and echo back, will wait until a message is received, or max 6000ms..
  WifiMessage in = wifi.listenForIncomingMessage(6000);
  if (in.hasData) {
    if (in.channel == SERVER)
      Serial.println("Message from the server:");
    else
      Serial.println("Message a local client:");
    Serial.println(in.message);
    //Echo back;
    wifi.send(in.channel, "Echo:", false);
    wifi.send(in.channel, in.message);
    nextPing = millis() + 10000;
  }

  //If you want do disconnect from the server use:
  // wifi.disconnectFromServer();




  
  

  
}
