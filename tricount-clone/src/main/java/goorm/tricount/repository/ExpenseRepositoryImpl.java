package goorm.tricount.repository;

import goorm.tricount.model.Expense;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class ExpenseRepositoryImpl implements ExpenseRepository {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Expense save(Expense expense) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("expense").usingGeneratedKeyColumns("id");

        Map<String, Object> params = new HashMap<>();
        params.put("name", expense.getName());
        params.put("settlement_id", expense.getSettlementId());
        params.put("payer_member_id", expense.getPayerMemberId());
        params.put("amount", expense.getAmount());
        params.put("expense_date_time", expense.getExpenseDateTime());

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(params));
        expense.setId(key.longValue());

        return expense;
    }
}