package com.agriculture.pyhtecserver.repositories;

import com.agriculture.pyhtecserver.models.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    Optional<Invoice> findByInvoiceNumber(String invoiceNumber);

    List<Invoice> findByCustomerId(Long customerId);

    List<Invoice> findByOrderId(Long orderId);

    List<Invoice> findByStatus(Invoice.InvoiceStatus status);

    @Query("SELECT i FROM Invoice i WHERE i.invoiceDate BETWEEN :startDate AND :endDate")
    List<Invoice> findInvoicesBetweenDates(@Param("startDate") LocalDateTime startDate,
                                            @Param("endDate") LocalDateTime endDate);

    @Query("SELECT i FROM Invoice i WHERE i.dueDate < :currentDate AND i.status = 'SENT'")
    List<Invoice> findOverdueInvoices(@Param("currentDate") LocalDateTime currentDate);

    @Query("SELECT SUM(i.totalAmount) FROM Invoice i WHERE i.status = 'PAID'")
    Double getTotalPaidAmount();

    @Query("SELECT SUM(i.totalAmount) FROM Invoice i WHERE i.status = 'SENT' OR i.status = 'OVERDUE'")
    Double getTotalUnpaidAmount();

    @Query("SELECT i FROM Invoice i ORDER BY i.invoiceDate DESC")
    List<Invoice> findAllOrderedByDate();
}
