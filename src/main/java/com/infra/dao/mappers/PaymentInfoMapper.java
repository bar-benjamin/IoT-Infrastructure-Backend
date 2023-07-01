package com.infra.dao.mappers;

import com.infra.representations.PaymentInfo;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PaymentInfoMapper implements ResultSetMapper<List<PaymentInfo>> {
    @Override
    public List<PaymentInfo> map(int index, ResultSet r, StatementContext ctx) throws SQLException {
        List<PaymentInfo> payments = new ArrayList<>();

        do {
            PaymentInfo paymentInfo = new PaymentInfo();

            paymentInfo.setPaymentID(r.getInt("payment_id"));
            paymentInfo.setCreditCard(r.getString("credit_card"));
            paymentInfo.setExpirationDate(r.getString("expiration_date"));
            paymentInfo.setCVV(r.getString("cvv"));

            payments.add(paymentInfo);
        } while(r.next());

        return payments;
    }
}
