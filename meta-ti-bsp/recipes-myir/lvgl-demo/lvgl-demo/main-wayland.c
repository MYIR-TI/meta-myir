#include "lvgl/lvgl.h"
#include "lvgl/demos/lv_demos.h"
#include "lv_drivers/wayland/wayland.h"
#include "lv_drivers/indev/evdev.h"
#include <unistd.h>
#include <pthread.h>
#include <time.h>
#include <sys/time.h>



int main(void)
{
	int m_dpi;
	lv_coord_t m_width,m_height;

	int display_widht = 1920;
	int display_height = 1080;
	int color_depth = 32;

	char * buf=NULL;

	lv_init();

	lv_wayland_init();
	lv_disp_t * disp;
	disp = lv_wayland_create_window(display_widht,display_height,"lvgl ethercat demo",lv_wayland_close_window);
	
	evdev_init();
	static lv_indev_drv_t indev_drv_1;
	lv_indev_drv_init(&indev_drv_1); /*Basic initialization*/
	indev_drv_1.type = LV_INDEV_TYPE_POINTER;
	
	/*This function will be called periodically (by the library) to get the mouse position and state*/
	indev_drv_1.read_cb = evdev_read;
	lv_indev_t *mouse_indev = lv_indev_drv_register(&indev_drv_1);
	
	
	  /*Set a cursor for the mouse*/
	LV_IMG_DECLARE(mouse_cursor_icon)
	lv_obj_t * cursor_obj = lv_img_create(lv_scr_act()); /*Create an image object for the cursor */
	lv_img_set_src(cursor_obj, &mouse_cursor_icon); 		  /*Set the image source*/
	lv_indev_set_cursor(mouse_indev, cursor_obj);			  /*Connect the image  object to the driver*/
	
	
	printf("register disp!\n");


    /*Create a Demo*/
    lv_demo_widgets();

    /*Handle LitlevGL tasks (tickless mode)*/
    while(1) {
        lv_timer_handler();
        usleep(5000);
		lv_tick_inc(5);
    }

    return 0;
}

