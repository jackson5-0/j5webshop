package training360.j5webshop.reports;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportService {

    @Autowired
    private ReportDao reportDao;

    public List<ReportOfOrders> listOrdersByMonthAndByStatus() {
        return reportDao.listOrdersByMonthAndByStatus();
    }

    public List<ReportOfProductSale> listDeliveredProductsByMonth() {
        return reportDao.listDeliveredProductsByMonth();
    }


}
