package com.hanthink.gps.pub.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.hanthink.gps.pub.dao.MessageInfoManageDao;
import com.hanthink.gps.pub.service.MessageInfoManageService;
import com.hanthink.gps.pub.vo.ExcelImportVO;
import com.hanthink.gps.pub.vo.MessageInfoVO;
import com.hanthink.gps.pub.vo.OrderInfoVO;
import com.hanthink.gps.pub.vo.PageObject;
import com.hanthink.gps.pub.vo.SuppGroupVO;
import com.hanthink.gps.util.Constants;
import com.hanthink.gps.util.FileUtil;
import com.hanthink.gps.util.StringUtil;
import com.hanthink.gps.util.logger.LogUtil;
import com.hanthink.gps.util.sequence.SeqManager;
import com.hanthink.gps.util.sequence.SeqType;

/**
 * 公告信息
 * @author Administrator
 *
 */
public class MessageInfoManageServiceImpl extends BaseServiceImpl implements MessageInfoManageService {

	private MessageInfoManageDao infoDao;

	public MessageInfoManageDao getInfoDao() {
		return infoDao;
	}
	public void setInfoDao(MessageInfoManageDao infoDao) {
		this.infoDao = infoDao;
	}
	
	/**
	 * 分页查询公告信息
	 * @param infoVO
	 * @param start
	 * @param limit
	 * @return
	 * @author zuosl 2016-4-7
	 */
	public PageObject queryMessageManageForPage(MessageInfoVO infoVO,
			int start, int limit) {
		return infoDao.queryMessageManageForPage(infoVO, start, limit);
	}
	
	/**
	 * 新增公告信息
	 * @param infoVO
	 * @author zuosl 2016-4-7
	 */
	public String addMessageInfo(MessageInfoVO infoVO, ExcelImportVO importVO) {
		
		String infoId = SeqManager.getSeq(SeqType.SEQ_INFONO);
		infoVO.setInfoId(infoId);
		
		//附件处理
		if(null != importVO && null != importVO.getAttachment()){
			if (importVO.getAttachment().length() > 1024 * 1024 * Constants.MAX_FILE_SIZE) {
				return "上传附件不能超过" + Constants.MAX_FILE_SIZE + "M";
			}
			String filePath = Constants.FILE_UPLOAD_PATH + File.separator + "messageinfo";
			try {
				InputStream stream = new FileInputStream(importVO.getAttachment());
				
				String path = infoVO.getFilePath();
				String fileName = path.substring(path.lastIndexOf("\\")+1);
				String fileType = fileName.substring(fileName.lastIndexOf('.'));
				
				//保存文件
				String hmi = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
				if (FileUtil.newFolder(filePath)) {
					filePath += (File.separator + hmi + "_" + infoId + fileType);
					OutputStream bos = new FileOutputStream(filePath);
					int bytesRead = 0;
					byte[] buffer = new byte[8192];
					while ((bytesRead = stream.read(buffer, 0, 8192)) != -1) {
						bos.write(buffer, 0, bytesRead);
					}
					bos.close();
					stream.close();
					
					infoVO.setFileName(fileName);
					infoVO.setFilePath(filePath);
					
				}else{
					return "创建文件夹失败";
				}
				
			} catch (Exception e) {
				LogUtil.error(e);
				e.printStackTrace();
				return "保存文件异常";
			}
		}
		
		// 新增公告信息
		infoDao.addMessageInfo(infoVO);
		
		//公告+供应商组处理
		String supGroupIds = infoVO.getSupGroupIdList();
		if(!StringUtil.isNullOrEmpty(supGroupIds)){
			String[] groupIdArr = supGroupIds.split(",");
			List<SuppGroupVO> groupVOs = new ArrayList<SuppGroupVO>();
			for(int i = 0; i < groupIdArr.length; i ++){
				if(!StringUtil.isNullOrEmpty(groupIdArr[i])){
					SuppGroupVO vo = new SuppGroupVO();
					vo.setInfoId(infoId);
					vo.setGroupId(groupIdArr[i]);
					groupVOs.add(vo);
				}
			}
			if(null != groupVOs && 0 < groupVOs.size()){
				infoDao.insertMessageInfoSupGroupId(groupVOs);
			}
		}
		
		return "1";
	}
	
	/**
	 * 修改公告信息
	 * @param infoVO
	 * @param importVO
	 * @return
	 * @author zuosl 2016-4-8
	 */
	public String updateMessageInfo(MessageInfoVO infoVO, ExcelImportVO importVO) {
		
		
		//附件处理
		if(null != importVO && null != importVO.getAttachment()){
			
			if (importVO.getAttachment().length() > 1024 * 1024 * Constants.MAX_FILE_SIZE) {
				return "上传附件不能超过" + Constants.MAX_FILE_SIZE + "M";
			}
			
			MessageInfoVO qInfoVO = infoDao.queryMessageInfoByInfoId(infoVO.getInfoId());
			
			//修改时有附件，先删除原附件
			if(null !=qInfoVO && !StringUtil.isNullOrEmpty(qInfoVO.getFilePath())){
				FileUtil.delFile(qInfoVO.getFilePath());
			}
			
			String filePath = Constants.FILE_UPLOAD_PATH + File.separator + "messageinfo";
			try {
				InputStream stream = new FileInputStream(importVO.getAttachment());
				
				String path = infoVO.getFilePath();
				String fileName = path.substring(path.lastIndexOf("\\")+1);
				String fileType = fileName.substring(fileName.lastIndexOf('.'));
				
				//保存文件
				String hmi = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
				if (FileUtil.newFolder(filePath)) {
					filePath += (File.separator + hmi + "_" + infoVO.getInfoId() + fileType);
					OutputStream bos = new FileOutputStream(filePath);
					int bytesRead = 0;
					byte[] buffer = new byte[8192];
					while ((bytesRead = stream.read(buffer, 0, 8192)) != -1) {
						bos.write(buffer, 0, bytesRead);
					}
					bos.close();
					stream.close();
					
					infoVO.setFileName(fileName);
					infoVO.setFilePath(filePath);
					
				}else{
					return "创建文件夹失败";
				}
				
			} catch (Exception e) {
				LogUtil.error(e);
				e.printStackTrace();
				return "保存文件异常";
			}
		}else{
			infoVO.setFileName(null);
			infoVO.setFilePath(null);
		}
		
		//修改公告信息
		infoDao.updateMessageInfo(infoVO);
		
		//删除该公告的供应商组信息
		infoDao.deleteMessageInfoSupGroupIdByInfoId(infoVO.getInfoId());
		
		//新增该公告的供应商组信息
		String supGroupIds = infoVO.getSupGroupIdList();
		if(!StringUtil.isNullOrEmpty(supGroupIds)){
			String[] groupIdArr = supGroupIds.split(",");
			List<SuppGroupVO> groupVOs = new ArrayList<SuppGroupVO>();
			for(int i = 0; i < groupIdArr.length; i ++){
				if(!StringUtil.isNullOrEmpty(groupIdArr[i])){
					SuppGroupVO vo = new SuppGroupVO();
					vo.setInfoId(infoVO.getInfoId());
					vo.setGroupId(groupIdArr[i]);
					groupVOs.add(vo);
				}
			}
			if(null != groupVOs && 0 < groupVOs.size()){
				infoDao.insertMessageInfoSupGroupId(groupVOs);
			}
		}
		
		return "1";
	}
	
	/**
	 * 删除公告信息
	 * @param infoVO
	 * @return
	 * @author zuosl 2016-4-8
	 */
	public String deleteMessageInfo(MessageInfoVO infoVO) {
		
		//公告有附件，需删除附件
		MessageInfoVO qInfoVO = infoDao.queryMessageInfoByInfoId(infoVO.getInfoId());
		if(null !=qInfoVO && !StringUtil.isNullOrEmpty(qInfoVO.getFilePath())){
			FileUtil.delFile(qInfoVO.getFilePath());
		}
		
		//删除公告信息
		infoDao.deleteMessageInfo(infoVO);
		
		return "1";
	}
	
	/**
	 * 查询首页公告信息
	 * @param infoVO
	 * @param start
	 * @param limit
	 * @return
	 * @author zuosl 2016-4-11
	 */
	public PageObject queryHomePageMessageForPage(MessageInfoVO infoVO,
			int start, int limit) {
		return infoDao.queryHomePageMessageForPage(infoVO, start, limit);
	}
	
	/**
	 * 更新供应商公告查看状态
	 * @param infoVO
	 * @return
	 * @author zuosl 2016-4-12
	 */
	public void updateSupplierViewStatus(MessageInfoVO infoVO) {
		infoDao.updateSupplierViewStatus(infoVO);
	}
	
	/**
	 * 根据公告ID查询公告信息
	 * @param infoId
	 * @return
	 * @author zuosl 2016-4-12
	 */
	public MessageInfoVO queryMessageInfoByInfoId(String infoId) {
		return infoDao.queryMessageInfoByInfoId(infoId);
	}
	
	/**
	 * 更新公告供应商下载状态
	 * @param infoVO
	 * @author zuosl 2016-4-12
	 */
	public void updateSupplierDownloadStatus(MessageInfoVO infoVO) {
		infoDao.updateSupplierDownloadStatus(infoVO);
	}
	
	/**
	 * 查询公告供应商查看记录
	 * @param infoId
	 * @param start
	 * @param limit
	 * @return
	 * @author zuosl 2016-4-12
	 */
	public PageObject queryHomePageMsgQueryStatusForPage(String infoId,
			int start, int limit) {
		
		return infoDao.queryHomePageMsgQueryStatusForPage(infoId, start, limit);
	}
	
	public PageObject queryOrderInfo(OrderInfoVO orderInfoVO, int start,
			int limit) {
		//获取用户权限
		List<String> strs = infoDao.queryPrivilegeInfo(orderInfoVO);
		
		if(strs.size()>0){
			List<OrderInfoVO> list = new ArrayList<OrderInfoVO>();
			List<OrderInfoVO> newList = new ArrayList<OrderInfoVO>();
			
			//统计订单状态信息
			PageObject po = infoDao.queryOrderInfo(orderInfoVO, start, limit);
			list=po.getItems();
			//筛选符合条件数据
			for(int i=0;i<list.size();i++){
				if(strs.contains(list.get(i).getOrderTypeName())){
					newList.add(list.get(i));
				}
			}
			return new PageObject(newList,newList.size());
		}else{
			return new PageObject(new ArrayList<OrderInfoVO>(),0);
		}
	}
	
	

}
