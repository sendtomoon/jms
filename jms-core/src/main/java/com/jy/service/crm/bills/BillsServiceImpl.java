package com.jy.service.crm.bills;

import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jy.dao.crm.bills.BillsDao;
import com.jy.entity.pos.billing.PosBill;
import com.jy.entity.pos.billing.PosBillDetail;
import com.jy.entity.crm.bills.Bills;
import com.jy.service.base.BaseServiceImp;

@Service("billsService")
public class BillsServiceImpl extends BaseServiceImp<Bills> implements BillsService{

	@Autowired
	private BillsDao billsDao;
	
	public PosBill findDetail(Bills bills) {
		Bills bill = billsDao.findBills(bills);
		PosBill posBill = billsDao.findPosBill(bill);
		List<PosBillDetail> list = billsDao.findDetail(bill);
		posBill.setPosBillDetails(list);
		return posBill;
	}

	@Override
	public void updateBills(String chks) {
		if(StringUtils.isNotBlank(chks)){
			String[] chk = chks.split(",");
			for (String id : chk) {
				billsDao.deleteBills(id);
			}
		}
	}
}
