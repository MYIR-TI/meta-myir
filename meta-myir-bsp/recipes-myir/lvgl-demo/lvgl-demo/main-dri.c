#include "lvgl/lvgl.h"
#include "lvgl/demos/lv_demos.h"
#include "lv_drivers/display/drm.h"
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

	drm_init();
	drm_get_sizes(&m_width,&m_height,&m_dpi);
	printf("m_widht [%d] m_height[%d] m_dpi[%d]\n",m_width,m_height,m_dpi);


	display_widht=m_width;
	display_height=m_height;
	color_depth=m_dpi;


	buf = malloc(m_width*m_height*sizeof(lv_color1_t));
	if(buf == NULL){
		printf("malloc error \n");
	}

	/*Initialize a descriptor for the buffer*/
	static lv_disp_draw_buf_t m_disp_buf;
	lv_disp_draw_buf_init(&m_disp_buf, buf, NULL, m_width*m_height);


	static lv_disp_drv_t disp_drv;
	lv_disp_drv_init(&disp_drv);
	disp_drv.draw_buf	= &m_disp_buf;
	disp_drv.flush_cb	= drm_flush;
	disp_drv.hor_res	= display_widht;
	disp_drv.ver_res	= display_height;
	lv_disp_drv_register(&disp_drv);
	
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

