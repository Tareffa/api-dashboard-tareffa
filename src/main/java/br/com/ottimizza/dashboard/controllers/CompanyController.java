//package br.com.ottimizza.dashboard.controllers;
//
//import br.com.ottimizza.dashboard.models.Company;
//import br.com.ottimizza.dashboard.models.users.User;
//import br.com.ottimizza.dashboard.services.CompanyService;
//import br.com.ottimizza.dashboard.services.UserService;
//
//import java.math.BigInteger;
//import java.security.Principal;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//import java.util.Optional;
//import javax.inject.Inject;
//
//import org.json.JSONArray;
//import org.json.JSONObject;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/company")
//public class CompanyController {

//    @Inject
//    CompanyService companyService;
// 
//    @Inject
//    UserService userService;
//
//    @PostMapping("save")
//    // <editor-fold defaultstate="collapsed" desc="Save company">
//    public ResponseEntity<Company> saveCompany(@RequestBody Company company) throws Exception {
//        return ResponseEntity.ok(companyService.save(company));
//    }
//    // </editor-fold>
//
//    @GetMapping("find/{id}")
//    // <editor-fold defaultstate="collapsed" desc="Find company by ID">
//    public ResponseEntity<Optional<Company>> findCompanyByID(Principal principal, @PathVariable("id") BigInteger idCompany)
//            throws Exception {
//
//        // Get Authorized User by Username.
//        User authorized = userService.findByUsername(principal.getName());
//
//        return ResponseEntity.ok(companyService.findById(idCompany));
//    }
//
//    @RequestMapping(value = "/find/cnpj", method = RequestMethod.POST, consumes = "application/json")
//    public ResponseEntity<List<Company>> findCompaniesByCNPJ(@RequestBody Map<String, List<String>> body)
//            throws Exception {
//        List<String> listaCNPJ = body.get("cnpj");
//        return ResponseEntity.ok(companyService.findByListCNPJ(listaCNPJ));
//    }
//    
//    @RequestMapping(value = "/find/email", method = RequestMethod.POST, consumes = "application/json")
//    public ResponseEntity<List<Company>>  searchCNPJ(@RequestBody Map<String,String> email){
//    	List<Company> resposta = new ArrayList<Company>();
//    	try {
//    		SalesForceService sForce = new SalesForceService();
//            JSONObject response = sForce.searchCNPJ(email.get("email"));
//            JSONArray listaJson = response.optJSONArray("records");
//            
//            List<String> listaCNPJ = new ArrayList();
//            
//            for (int i = 0; i < listaJson.length(); i++) {
//            	listaCNPJ.add(listaJson.get(i).toString());
//			}
//            resposta = companyService.findByListCNPJ(listaCNPJ);
//        } catch (Exception e) { }
//        return ResponseEntity.ok(resposta);
//
//    }
//    
//    @PutMapping("update/{id}")
//    public ResponseEntity<String> updateCompany(@PathVariable("id") BigInteger idCompany, @RequestBody Company company)
//            throws Exception {
//        return ResponseEntity.ok(companyService.updateById(idCompany, company).toString());
//    }
//
//    @RequestMapping(value = "/deleteAllKpi", method = RequestMethod.POST, consumes = "application/json")
//    public ResponseEntity<String> deleteAllInformationByCNPJ(@RequestBody Map<String,String> cnpj)
//            throws Exception {
//        return ResponseEntity.ok(companyService.deleteAllInformationByCNPJ(cnpj.get("cnpj")).toString());
//    }
//    
//    @DeleteMapping("delete/{id}")
//    public ResponseEntity<String> removeCompany(@PathVariable("id") BigInteger idCompany) throws Exception {
//        return ResponseEntity.ok(companyService.delete(idCompany).toString());
//    }

//}
