#include <SerialESP8266wifi.h>

#include <Adafruit_ESP8266.h>



void setup()
{
  Serial.begin(115200);
  Serial.println();

  WiFi.begin("Razer Phone", "danielsabo1");

  Serial.print("Connecting");
  while (WiFi.status() != WL_CONNECTED)
  {
    delay(500);
    Serial.print(".");
  }
  Serial.println();

  Serial.print("Connected, IP address: ");
  Serial.println(WiFi.localIP());
}

void loop() {}
