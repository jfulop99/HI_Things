package hu.hotelinteractive.issuetracker.issues;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import hu.hotelinteractive.issuetracker.customer.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

@Controller
@RequestMapping("/issues")
public class IssueController {

    @Autowired
    private IssueService issueService;

    @Autowired
    private CustomerService customerService;



    @GetMapping("/")
    public String viewAllIssues(Model model) {

        return findPaginated(1, "id", "desc", "-- Minden partner --",
                LocalDate.of(2014, 1, 1).toString(), LocalDate.now().toString(), "-- Minden kategória --", model);

    }

    @GetMapping("/showNewIssueForm")
    public String showNewIssueForm(Model model) {
        // create model attribute to bind form data
        Issue issue = new Issue();
        issue.setOpenDate(LocalDate.now());
        issue.setWorkHours(0);
        model.addAttribute("issue", issue);
        model.addAttribute("issueGroups", issueService.getAllIssueGroup());
        model.addAttribute("customers", customerService.getAllCustomerSortByName());
        return "issueForm";
    }

    @PostMapping("/saveIssue")
    public String saveIssue(@Valid Issue issue, BindingResult bindingResult, Model model) {
        // save employee to database
        if (bindingResult.hasErrors()) {
            model.addAttribute("issueGroups", issueService.getAllIssueGroup());
            model.addAttribute("customers", customerService.getAllCustomerSortByName());
            return "issueForm";
        }

        if (issue.getRegId() == 0) {
            issue.setRegId(issueService.getNewRegId());
        }
        issueService.saveIssue(issue);
        return "redirect:/issues/";
    }

    @GetMapping("/showFormForUpdate/{id}")
    public String showFormForUpdate(@PathVariable ( value = "id") long id, Model model) {

        // get employee from the service
        Issue issue = issueService.getIssueById(id);

        // set employee as a model attribute to pre-populate the form
        model.addAttribute("issue", issue);
        model.addAttribute("issueGroups", issueService.getAllIssueGroup());
        model.addAttribute("customers", customerService.getAllCustomerSortByName());
        return "issueForm";
    }

    @GetMapping("/deleteIssue/{id}")
    public String deleteIssue(@PathVariable (value = "id") long id) {

        // call delete issue method
        issueService.deleteIssueById(id);
        return "redirect:/issues/";
    }

    @GetMapping("/page/{pageNo}")
    public String findPaginated(@PathVariable(value = "pageNo") int pageNo,
                                @RequestParam("sortField") String sortField,
                                @RequestParam("sortDir") String sortDir,
                                @RequestParam("customerName") String customerName,
                                @RequestParam("dateStart") String dateStart,
                                @RequestParam("dateEnd") String dateEnd,
                                @RequestParam("issueGroupName") String issueGroupName,
                                Model model) {
        int pageSize = 8;

        Page<Issue> page = issueService.findPaginated(pageNo, pageSize, sortField, sortDir, customerName,
                LocalDate.parse(dateStart), LocalDate.parse(dateEnd), issueGroupName);
        List<Issue> listIssues = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("listIssues", listIssues);

        model.addAttribute("customerName", customerName);
        model.addAttribute("customers", customerService.getAllCustomerSortByName());
        model.addAttribute("issueGroupName", issueGroupName);
        model.addAttribute("issueGroups", issueService.getAllIssueGroup());

        model.addAttribute("dateStart", dateStart);
        model.addAttribute("dateEnd", dateEnd);



        return "issues";
    }

    public ByteArrayInputStream pdfGenerator(List<Issue> issues) throws IOException, DocumentException, URISyntaxException {

        Document document = new Document(PageSize.A4.rotate(), 0f, 0f, 75f, 50f);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PdfWriter writer = PdfWriter.getInstance(document, out);

        PdfHeaderFooterPageEvent event = new PdfHeaderFooterPageEvent();

        writer.setPageEvent(event);

        document.open();

        PdfPTable table = new PdfPTable(10);
        table.setWidthPercentage(90f);
        table.setWidths(new float[] {1,1,3,3,5,3,3,5,1,2});
        table.setHeaderRows(1);
        List<String> headers = List.of("Id", "Szám", "Bejelentés", "Partner", "Leírás", "Csoport", "Befejezés", "Megoldás", "Óra", "Munkalap");
//        addCustomRows(table);
        addTableHeader(table, headers);
        addRows(table, issues);

        document.add(table);
        document.close();
        return new ByteArrayInputStream(out.toByteArray());
    }

    private void addTableHeader(PdfPTable table, List<String> headers) {
        Font fontH1 = new Font(Font.FontFamily.COURIER, 10, Font.NORMAL);
        headers.stream()
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setBorderWidth(2);
                    header.setPhrase(new Phrase(columnTitle, fontH1));
                    table.addCell(header);
                });
    }

    private void addRows(PdfPTable table, List<Issue> issues) {
        Font fontH1 = new Font(Font.FontFamily.COURIER, 10, Font.NORMAL);
        for (Issue item: issues ) {
            table.addCell(new Phrase(String.valueOf(item.getId()), fontH1));
            table.addCell(new Phrase(String.valueOf(item.getRegId()), fontH1));
            table.addCell(new Phrase(item.getOpenDate().toString(), fontH1));
            table.addCell(new Phrase(item.getCustomerName(), fontH1));
            table.addCell(new Phrase(item.getIssueText(), fontH1));
            table.addCell(new Phrase(item.getIssueGroup(), fontH1));
            table.addCell(new Phrase(item.getCloseDate() == null ? "" : item.getCloseDate().toString(), fontH1));
            table.addCell(new Phrase(item.getIssueResolution(), fontH1));
            table.addCell(new Phrase(String.valueOf(item.getWorkHours()), fontH1));
            table.addCell(new Phrase(item.getWorksheet(), fontH1));
        }
    }

    private void addCustomRows(PdfPTable table)
            throws BadElementException, IOException {
        Path path = Path.of("logohi2018-100x97.jpg");
        Image img = Image.getInstance(path.toAbsolutePath().toString());
        img.scalePercent(100);

        PdfPCell imageCell = new PdfPCell(img);
        table.addCell(imageCell);

        PdfPCell horizontalAlignCell = new PdfPCell(new Phrase("row 2, col 2"));
        horizontalAlignCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(horizontalAlignCell);

        PdfPCell verticalAlignCell = new PdfPCell(new Phrase("row 2, col 3"));
        verticalAlignCell.setVerticalAlignment(Element.ALIGN_BOTTOM);
        table.addCell(verticalAlignCell);
    }


    @GetMapping(value = "/teszt", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity Report(@RequestParam("sortField") String sortField,
                                 @RequestParam("sortDir") String sortDir,
                                 @RequestParam("customerName") String customerName,
                                 @RequestParam("dateStart") String dateStart,
                                 @RequestParam("dateEnd") String dateEnd,
                                 @RequestParam("issueGroupName") String issueGroupName)
            throws IOException, URISyntaxException, DocumentException {

        List<Issue> result = issueService.findForPrinting(LocalDate.parse(dateStart), LocalDate.parse(dateEnd), customerName, issueGroupName, sortField, sortDir);

        ByteArrayInputStream bis = pdfGenerator(result);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=customers.pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }


}
