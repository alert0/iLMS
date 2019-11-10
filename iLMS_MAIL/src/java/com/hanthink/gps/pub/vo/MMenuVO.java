/**
 * 
 */
package com.hanthink.gps.pub.vo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang.math.RandomUtils;

import com.hanthink.gps.pub.vo.BaseVO;
import com.hanthink.gps.util.StringUtil;


/**
 * @author zhangyingchun
 *
 */
public class MMenuVO extends BaseVO{
    private static final long serialVersionUID = 6102711891148268681L;
    // 该级菜单包含的下一级菜单
    private List<MMenuVO> menuList = new ArrayList<MMenuVO>();
    /** 链接地址 */
    private String url;
    /** 菜单编号 */
    private String menuId;
    /** 菜单名称 */
    private String menuName;
    /** 菜单级别 */
    private String menuLevel;
    /** 父模块代码 */
    private String parentId;
    /** 排序 */
    private String sort;
    
    
    
    
    private String menuDesc;
    private String urlTarget;
    private String dispOrder;
    /** 子帐号 */
    private String subRoleType;
    /** 身份类型 */
    private String type;
    private String userId;
    @SuppressWarnings("unused")
	private boolean needAutoShowFlg;
    
    

    public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}

	public List<MMenuVO> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<MMenuVO> menuList) {
        this.menuList = menuList;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuDesc() {
        return menuDesc;
    }

    public void setMenuDesc(String menuDesc) {
        this.menuDesc = menuDesc;
    }

    public String getUrlTarget() {
        return urlTarget;
    }

    public void setUrlTarget(String urlTarget) {
        this.urlTarget = urlTarget;
    }

    public String getMenuLevel() {
        return menuLevel;
    }

    public void setMenuLevel(String menuLevel) {
        this.menuLevel = menuLevel;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getDispOrder() {
        return dispOrder;
    }

    public void setDispOrder(String dispOrder) {
        this.dispOrder = dispOrder;
    }

    public boolean isNeedAutoShowFlg(String extendMenuId) {
        // 看是否有指定的menuId需要展开
        if (StringUtil.isBlank(extendMenuId)) {
            // 没有的话证明只有在唯一的一个一级菜单下有需要展开的菜单
            if (this.menuId != null) {
                switch (Integer.valueOf(this.menuId)) {
                case 181:
                    return true;
                case 454:
                    return true;
                default:
                    return false;
                }
            }
        } else {
            // 展开指定的一级菜单下的二级菜单目录
            if (this.menuId.equals(extendMenuId)) {
                return true;
            }
        }
        return false;
    }

    public void setNeedAutoShowFlg(boolean needAutoShowFlg) {
        this.needAutoShowFlg = needAutoShowFlg;
    }

	/**
	 * 获取 userId
	 *
	 * @return userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * 设定userId
	 *
	 * @param userId userId
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * 获取 type
	 *
	 * @return type
	 */
	public String getType() {
		return type;
	}

	/**
	 * 设定type
	 *
	 * @param type type
	 */
	public void setType(String type) {
		this.type = type;
	}

	public String getSubRoleType() {
		return subRoleType;
	}

	public void setSubRoleType(String subRoleType) {
		this.subRoleType = subRoleType;
	}

	public static void main(String[] args) {
//		System.out.println(RandomUtils.nextInt(1));
		
		List<MMenuVO> allList = new ArrayList<MMenuVO>();
		for(int i = 0; i < 3; i ++){
			MMenuVO vo = new MMenuVO();
			vo.setMenuLevel("1");
			vo.setMenuId("M01-"+i);
			vo.setMenuName("一级模块"+i);
			vo.setParentId("");
			allList.add(vo);
		}
		
		for(int i = 0; i < 5; i ++){
			MMenuVO vo = new MMenuVO();
			vo.setMenuLevel("2");
			vo.setMenuId("M02-"+i);
			vo.setMenuName("二级模块"+i);
			int r = RandomUtils.nextInt(3);
			vo.setParentId("M01-"+r);
			allList.add(vo);
		}
		
		for(int i = 0; i < 10; i ++){
			MMenuVO vo = new MMenuVO();
			vo.setMenuLevel("3");
			vo.setMenuId("M03-"+i);
			vo.setMenuName("三级菜单"+i);
			vo.setUrl("pages/v"+i);
			int r = RandomUtils.nextInt(5);
			vo.setParentId("M02-"+r);
			allList.add(vo);
		}
		
		List<MMenuVO> menuList = new ArrayList<MMenuVO>();
		List<MMenuVO> topList = new ArrayList<MMenuVO>();
		List<MMenuVO> secList = new ArrayList<MMenuVO>();
		List<MMenuVO> thiList = new ArrayList<MMenuVO>();
		
		for(MMenuVO menuVO : allList){
			if("1".equals(menuVO.getMenuLevel())){
				topList.add(menuVO);
			}else if("2".equals(menuVO.getMenuLevel())){
				secList.add(menuVO);
			}else if("3".equals(menuVO.getMenuLevel())){
				thiList.add(menuVO);
			}
		}
		
		if(null == topList || 0 >= topList.size()
				|| null == secList || 0 >= secList.size()
				|| null == thiList || 0 >= thiList.size() ){
			return;
		}
		
		int count = 0;
		for(MMenuVO menuVO1 : topList){
			List<MMenuVO> menuList2 = new ArrayList<MMenuVO>();
			for(MMenuVO menuVO2 : secList){
				if(menuVO1.getMenuId().equals(menuVO2.getParentId())){
					List<MMenuVO> menuList3 = new ArrayList<MMenuVO>();
					for(MMenuVO menuVO3 : thiList){
						if(menuVO2.getMenuId().equals(menuVO3.getParentId())){
							menuList3.add(menuVO3);
							count ++;
						}
					}
					menuVO2.setMenuList(menuList3);
					menuList2.add(menuVO2);
				}
			}
			menuVO1.setMenuList(menuList2);
			menuList.add(menuVO1);
		}
		
		
		int count2 = 0;
		
		//结果
		for(MMenuVO mvo : menuList){
			System.out.println("------------------------");
			System.out.println(mvo.getMenuId()+","+mvo.getMenuName());
			List<MMenuVO> m2 = mvo.getMenuList();
			for(MMenuVO v2 : m2){
				System.out.println("  "+v2.getMenuId()+","+v2.getMenuName());
				List<MMenuVO> m3 = v2.getMenuList();
				for(MMenuVO v3 : m3){
					System.out.println("    "+v3.getMenuId()+","+v3.getMenuName());
					count2++;
				}
			}
		}
		
		System.out.println(count);
		System.out.println(count2);
		
	}
	
}
