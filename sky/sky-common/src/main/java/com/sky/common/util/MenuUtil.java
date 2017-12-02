package com.sky.common.util;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

import com.soecode.wxtools.api.IService;
import com.soecode.wxtools.api.WxConsts;
import com.soecode.wxtools.bean.WxMenu;
import com.soecode.wxtools.bean.WxMenu.WxMenuButton;
import com.soecode.wxtools.exception.WxErrorException;

public class MenuUtil {
	private static Logger logger = Logger.getLogger(MenuUtil.class);
	/**
	 * 主菜单
	 * @return
	 */
	public static void createMenu(IService iService){
		WxMenu menu = new WxMenu();
	    List<WxMenuButton> btnList = new ArrayList<>();
	    //设置含有子按钮的按钮
	    List<WxMenuButton> subList = new ArrayList<>();
	    //子按钮
	    WxMenuButton btnSub1 = new WxMenuButton();
	    btnSub1.setType(WxConsts.BUTTON_CLICK);
	    btnSub1.setKey("btnSub1Key");
	    btnSub1.setName("查看身份");
	    WxMenuButton btnSub2 = new WxMenuButton();
	    btnSub2.setType(WxConsts.BUTTON_CLICK);
	    btnSub2.setKey("btnSub2Key");
	    btnSub2.setName("录入生日");
	    subList.add(btnSub1);
	    subList.add(btnSub2);
	    //把子按钮列表设置进按钮3
	    WxMenuButton btn1 = new WxMenuButton();
	    btn1.setName("个人信息");
	    btn1.setSub_button(subList);
	    //将三个按钮设置进btnList
	    btnList.add(btn1);
	    //设置进菜单类
	    menu.setButton(btnList);
	    try {
			iService.createMenu(menu, false);
		} catch (WxErrorException e) {
			e.printStackTrace();
			logger.error(e);
		}
	}
}
