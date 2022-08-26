package com.invoice.repository;

import com.invoice.entity.Country;
import com.invoice.entity.InvoiceItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@EnableJpaRepositories
public interface InvoiceItemRepository extends JpaRepository<InvoiceItem, UUID>
{
    @Query("select c from InvoiceItem c where c.invoiceId=:invoiceId")
    public List<InvoiceItem> findByInvoiceId(@Param("invoiceId") UUID invoiceId);

//    @Query("delete from InvoiceItem c where c.invoiceId=:invoiceId")
//    public void deleteByInvoiceId(@Param("invoiceId") UUID invoiceId);

}
