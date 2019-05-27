#!/usr/bin/env python3

import RPi.GPIO as GPIO
import SimpleMFRC522

reader = SimpleMFRC522.SimpleMFRC522()
id, text = reader.read()
print (id)
GPIO.cleanup()
