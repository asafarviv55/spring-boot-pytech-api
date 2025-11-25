package com.agriculture.pyhtecserver.controllers;

import com.agriculture.pyhtecserver.models.Invoice;
import com.agriculture.pyhtecserver.services.InvoiceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    @GetMapping
    public ResponseEntity<List<Invoice>> getAllInvoices() {
        log.info("Getting all invoices");
        return ResponseEntity.ok(invoiceService.getAllInvoices());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Invoice> getInvoiceById(@PathVariable Long id) {
        log.info("Getting invoice by id: {}", id);
        return invoiceService.getInvoiceById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Invoice> createInvoice(@RequestBody Invoice invoice) {
        log.info("Creating new invoice: {}", invoice.getInvoiceNumber());
        Invoice created = invoiceService.createInvoice(invoice);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Invoice> updateInvoice(@PathVariable Long id, @RequestBody Invoice invoice) {
        log.info("Updating invoice with id: {}", id);
        try {
            Invoice updated = invoiceService.updateInvoice(id, invoice);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInvoice(@PathVariable Long id) {
        log.info("Deleting invoice with id: {}", id);
        invoiceService.deleteInvoice(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/number/{invoiceNumber}")
    public ResponseEntity<Invoice> getInvoiceByNumber(@PathVariable String invoiceNumber) {
        log.info("Getting invoice by number: {}", invoiceNumber);
        return invoiceService.getInvoiceByNumber(invoiceNumber)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Invoice>> getInvoicesByCustomer(@PathVariable Long customerId) {
        log.info("Getting invoices for customer: {}", customerId);
        return ResponseEntity.ok(invoiceService.getInvoicesByCustomer(customerId));
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<Invoice>> getInvoicesByOrder(@PathVariable Long orderId) {
        log.info("Getting invoices for order: {}", orderId);
        return ResponseEntity.ok(invoiceService.getInvoicesByOrder(orderId));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Invoice>> getInvoicesByStatus(@PathVariable Invoice.InvoiceStatus status) {
        log.info("Getting invoices by status: {}", status);
        return ResponseEntity.ok(invoiceService.getInvoicesByStatus(status));
    }

    @GetMapping("/overdue")
    public ResponseEntity<List<Invoice>> getOverdueInvoices() {
        log.info("Getting overdue invoices");
        return ResponseEntity.ok(invoiceService.getOverdueInvoices());
    }

    @GetMapping("/total-paid")
    public ResponseEntity<Double> getTotalPaidAmount() {
        log.info("Getting total paid amount");
        return ResponseEntity.ok(invoiceService.getTotalPaidAmount());
    }

    @GetMapping("/total-unpaid")
    public ResponseEntity<Double> getTotalUnpaidAmount() {
        log.info("Getting total unpaid amount");
        return ResponseEntity.ok(invoiceService.getTotalUnpaidAmount());
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Invoice> updateInvoiceStatus(@PathVariable Long id, @RequestParam Invoice.InvoiceStatus status) {
        log.info("Updating invoice {} status to {}", id, status);
        try {
            Invoice updated = invoiceService.updateInvoiceStatus(id, status);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/date-range")
    public ResponseEntity<List<Invoice>> getInvoicesBetweenDates(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        log.info("Getting invoices between {} and {}", startDate, endDate);
        return ResponseEntity.ok(invoiceService.getInvoicesBetweenDates(startDate, endDate));
    }
}
