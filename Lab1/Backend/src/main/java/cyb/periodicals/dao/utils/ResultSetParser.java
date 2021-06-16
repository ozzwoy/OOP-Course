package cyb.periodicals.dao.utils;

import cyb.periodicals.entities.periodical.Periodical;
import cyb.periodicals.entities.periodical.PeriodicalBuilder;
import cyb.periodicals.entities.receipt.Receipt;
import cyb.periodicals.entities.receipt.ReceiptBuilder;
import cyb.periodicals.entities.subscription.Period;
import cyb.periodicals.entities.subscription.Subscription;
import cyb.periodicals.entities.subscription.SubscriptionBuilder;
import cyb.periodicals.entities.user.Role;
import cyb.periodicals.entities.user.User;
import cyb.periodicals.entities.user.UserBuilder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public abstract class ResultSetParser {

    public static User getUser(ResultSet resultSet) throws SQLException {
        UserBuilder builder = new UserBuilder()
                .setId(resultSet.getLong(1))
                .setUsername(resultSet.getString(2))
                .setPassword(resultSet.getString(3))
                .setRole(Role.valueOf(resultSet.getString(4)));

        builder.setName(resultSet.getString(5));
        if (resultSet.wasNull()) {
            builder.setName(null);
        }
        builder.setAddress(resultSet.getString(6));
        if (resultSet.wasNull()) {
            builder.setAddress(null);
        }

        return builder.build();
    }

    public static Periodical getPeriodical(ResultSet resultSet) throws SQLException {
        return new PeriodicalBuilder()
                .setId(resultSet.getLong(1))
                .setName(resultSet.getString(2))
                .setDescription(resultSet.getString(3))
                .setImage(resultSet.getString(4))
                .setPriceForMonth(resultSet.getInt(5))
                .setPriceForYear(resultSet.getInt(6))
                .setAvailable(resultSet.getBoolean(7))
                .build();
    }

    public static Receipt getReceipt(ResultSet resultSet) throws SQLException {
        return new ReceiptBuilder()
                .setId(resultSet.getLong(1))
                .setSum(resultSet.getInt(2))
                .setDoneAt(resultSet.getObject(3, LocalDateTime.class))
                .build();
    }

    public static Subscription getSubscription(ResultSet resultSet) throws SQLException {
        SubscriptionBuilder builder = new SubscriptionBuilder()
                .setId(resultSet.getLong(1))
                .setUserId(resultSet.getLong(2))
                .setPeriodicalId(resultSet.getLong(3))
                .setConfirmed(resultSet.getBoolean(4));

        if (resultSet.wasNull()) {
            builder.setReceiptId(null);
        }
        builder.setStartDate(resultSet.getDate(5).toLocalDate());
        if (resultSet.wasNull()) {
            builder.setStartDate(null);
        }
        builder.setEndDate(resultSet.getDate(6).toLocalDate());
        if (resultSet.wasNull()) {
            builder.setEndDate(null);
        }
        String periodStr = resultSet.getString(7);
        if (resultSet.wasNull()) {
            builder.setPeriod(null);
        } else {
            builder.setPeriod(Period.valueOf(periodStr));
        }
        builder.setReceiptId(resultSet.getLong(8));
        if (resultSet.wasNull()) {
            builder.setReceiptId(null);
        }

        return builder.build();
    }
}
