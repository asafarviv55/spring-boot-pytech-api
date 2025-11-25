package com.agriculture.pyhtecserver.services;

import com.agriculture.pyhtecserver.models.Invoice;
import com.agriculture.pyhtecserver.repositories.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    public List<Invoice> getAllInvoices() {
        return invoiceRepository.findAll();
    }

    public Optional<Invoice> getInvoiceById(Long id) {
        return invoiceRepository.findById(id);
    }

    public Invoice createInvoice(Invoice invoice) {
        invoice.calculateTotal();
        return invoiceRepository.save(invoice);
    }

    public Invoice updateInvoice(Long id, Invoice invoiceDetails) {
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Invoice not found with id: " + id));

        invoice.setInvoiceNumber(invoiceDetails.getInvoiceNumber());
        invoice.setOrder(invoiceDetails.getOrder());
        invoice.setCustomer(invoiceDetails.getCustomer());
        invoice.setDueDate(invoiceDetails.getDueDate());
        invoice.setSubtotal(invoiceDetails.getSubtotal());
        invoice.setTax(invoiceDetails.getTax());
        invoice.setDiscount(invoiceDetails.getDiscount());
        invoice.setStatus(invoiceDetails.getStatus());
        invoice.setNotes(invoiceDetails.getNotes());
        invoice.setPaidDate(invoiceDetails.getPaidDate());
        invoice.calculateTotal();

        return invoiceRepository.save(invoice);
    }

    public void deleteInvoice(Long id) {
        invoiceRepository.deleteById(id);
    }

    public Optional<Invoice> getInvoiceByNumber(String invoiceNumber) {
        return invoiceRepository.findByInvoiceNumber(invoiceNumber);
    }

    public List<Invoice> getInvoicesByCustomer(Long customerId) {
        return invoiceRepository.findByCustomerId(customerId);
    }

    public List<Invoice> getInvoicesByOrder(Long orderId) {
        return invoiceRepository.findByOrderId(orderId);
    }

    public List<Invoice> getInvoicesByStatus(Invoice.InvoiceStatus status) {
        return invoiceRepository.findByStatus(status);
    }

    public List<Invoice> getInvoicesBetweenDates(LocalDateTime startDate, LocalDateTime endDate) {
        return invoiceRepository.findInvoicesBetweenDates(startDate, endDate);
    }

    public List<Invoice> getOverdueInvoices() {
        return invoiceRepository.findOverdueInvoices(LocalDateTime.now());
    }

    public Double getTotalPaidAmount() {
        return invoiceRepository.getTotalPaidAmount();
    }

    public Double getTotalUnpaidAmount() {
        return invoiceRepository.getTotalUnpaidAmount();
    }

    public Invoice updateInvoiceStatus(Long id, Invoice.InvoiceStatus status) {
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Invoice not found with id: " + id));
        invoice.setStatus(status);
        if (status == Invoice.InvoiceStatus.PAID) {
            invoice.setPaidDate(LocalDateTime.now());
        }
        return invoiceRepository.save(invoice);
    }
}
