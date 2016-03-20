/**
 * keyboard.c
 *
 * This class takes full control over the keyboard buffer.
 *
 * Program written by B[]
 **/

#include <X11/XKBlib.h>
#include <X11/extensions/XInput2.h>
/* For printf */
#include <stdio.h>
#include <stdlib.h>

int main(int argc, char** argv){
  Display *dpy;
  int quit = 0;
  char *s;
  unsigned int kc;
  int count;
  count = atoi(argv[1]);
  if((dpy = XOpenDisplay(NULL)) == NULL) {
    perror(argv[0]);
    return 1;
  }
  XGrabKeyboard(dpy, DefaultRootWindow(dpy), False, GrabModeAsync, GrabModeAsync, CurrentTime);
  while(count > 0) {
    XEvent ev;
    XNextEvent(dpy, &ev);
    switch (ev.type) {
      case KeyPress :
        kc = ((XKeyPressedEvent*)&ev)->keycode;
        s = XKeysymToString(XKeycodeToKeysym(dpy, kc, 0));
        if(s){
          if(s != 13){
            printf("%s", s);
            count--;
          }
        }
        break;
    }
  }
  XUngrabKeyboard(dpy, CurrentTime);
  if (XCloseDisplay(dpy)) {
    perror(argv[0]);
    return 1;
  }
  return 0;
}
