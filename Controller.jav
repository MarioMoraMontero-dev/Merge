public class CotizadorController {
   public String stepNo {get;set;}
    public String opportunityId {get;set;}
    public String paquete {get;set;}
    public String productId {get;set;}
    public String keykits {get;set;}
    //Variable global para capturar el ID de la lista de precios de la opp
    public String IdListaPrecios {get;set;}
    public Decimal DescuentoTest{get;set;}
    public Account account {get;set;}
    public Contact contact {get;set;}
    public Opportunity opportunity {get;set;}
    public paquete__c paqueteRecord {get;set;}
    
    public List<SelectOption> paqueteList {get;set;}
    public List<SelectOption> currencyList {get;set;}
    public List<SelectOption> nivelDescuentoList {get;set;}
    public List<SelectOption> productTypeList {get;set;}
    public List<nivelDescuento__c> lstDiscount{get;set;}
    public List<ProductWrapper> oliServiceList {get;set;}
    public List<ProductWrapper> oliServiceOtrosList {get;set;}
    public List<ProductWrapper> oliInsumosList {get;set;}
    public List<ProductWrapper> oliInsumosOtrosList {get;set;}
    public List<ProductWrapper> oliMedicamentosList {get;set;}
    public List<ProductWrapper> oliMedicamentosOtrosList {get;set;}
    public List<ProductWrapper> oliHonorariosList {get;set;}
    public List<ProductWrapper> oliHonorariosOtrosList {get;set;}
    public List<ProductWrapper> oliOtrosList {get;set;}
    public List<ProductWrapper> oliExternalList {get;set;}
    
    public List<ProductWrapper> oliServiceListPDF {get;set;}
    public List<ProductWrapper> oliServiceOtrosListPDF {get;set;}
    public List<ProductWrapper> oliInsumosListPDF {get;set;}
    public List<ProductWrapper> oliInsumosOtrosListPDF {get;set;}
    public List<ProductWrapper> oliMedicamentosListPDF {get;set;}
    public List<ProductWrapper> oliMedicamentosOtrosListPDF {get;set;}
    public List<ProductWrapper> oliHonorariosListPDF {get;set;}
    public List<ProductWrapper> oliHonorariosOtrosListPDF {get;set;}
    public List<ProductWrapper> oliOtrosListPDF {get;set;}
    public List<ProductWrapper> oliExternalListPDF {get;set;}

    public decimal serviceTotal {get;set;}
    public decimal serviceOtrosTotal {get;set;}
    public decimal insumosTotal {get;set;}
    public decimal insumosOtrosTotal {get;set;}
    public decimal medicamentosTotal {get;set;}
    public decimal medicamentosOtrosTotal {get;set;}
    public decimal honorariosTotal {get;set;}
    public decimal honorariosOtrosTotal {get;set;}
    public decimal otrosTotal {get;set;}
    public decimal externalTotal {get;set;}
    
    public decimal serviceTotalSINDESCUENTO {get;set;}
    public decimal serviceOtrosTotalSINDESCUENTO {get;set;}
    public decimal insumosTotalSINDESCUENTO {get;set;}
    public decimal insumosOtrosTotalSINDESCUENTO {get;set;}
    public decimal medicamentosTotalSINDESCUENTO {get;set;}
    public decimal medicamentosOtrosTotalSINDESCUENTO {get;set;}
    public decimal honorariosTotalSINDESCUENTO {get;set;}
    public decimal honorariosOtrosTotalSINDESCUENTO {get;set;}
    public decimal otrosTotalSINDESCUENTO {get;set;}
    public decimal externalTotalSINDESCUENTO {get;set;}
   	//Var descuento
    public boolean checkActivoDescuento{get;set;}
    public decimal valorDescuentoActual{get; set;}
    public String bloquearProceso{get; set;}
    ///////
    public decimal serviceIVATotal {get;set;}
    public decimal serviceOtrosIVATotal {get;set;}
    public decimal insumosIVATotal {get;set;}
    public decimal insumosOtrosIVATotal {get;set;}
    public decimal medicamentosIVATotal {get;set;}
    public decimal medicamentosOtrosIVATotal {get;set;}
    public decimal honorariosIVATotal {get;set;}
    public decimal honorariosOtrosIVATotal {get;set;}
    public decimal otrosIVATotal {get;set;}
    public decimal externalIVATotal {get;set;}
    //Acciones del margen
    public boolean ActivarMargen {get;set;}
    
    
    public decimal montoTotalOpp {get;set;}
    public decimal montoTotalIVAOpp {get;set;}
    public decimal montoTotalOppConDescuento {get;set;}
    public decimal montoFinalOpp {get;set;}
    
    public Id IdPerfil {get; set;}
    public String NombreDelPerfil {get; set;}
    
    
    public String nivelDescuento {get;set;}
    public boolean showChildProduct {get;set;}
    public boolean otros {get;set;}
    public String language {get;set;}
    public Decimal discountPercentage {get;set;}
    public String discountName {get;set;}

    public List<OpportunityLineItem> oliDeleteList {get;set;}
    public Map<String, String> mapRenamePackageName {get; set;}
    public String oldValue {get; set;}
    public String newValue {get; set;}
    public Decimal Lindora {get; set;}
    public Decimal SanJose {get; set;}
    public Map<String, Decimal> mapSubPackagePackageAmount {get; set;}
    public Map<String, Decimal> mapProductNameAmount {get; set;}
    public Map<String, Decimal> mapPackageNameAmount {get; set;}
    public Map<String, Decimal> mapMainDiscountAmount {get; set;}
    public CotizadorController(ApexPages.StandardController controller) {
        init();
    }
    
    public void init()
    { 
        IdPerfil= userinfo.getProfileId();
        NombreDelPerfil=[Select Id,Name from Profile where Id=:IdPerfil].Name;
        system.debug('Perfil: '+NombreDelPerfil);
        if(NombreDelPerfil == 'CCM'){
            ActivarMargen = true;
        }else{
            ActivarMargen = false;
        }
       
        lstDiscount = new List<nivelDescuento__c>();
        bloquearProceso = 'false';
        keykits = 'K';
        Lindora = 0;
        SanJose = 0;
        stepNo = '2';
        opportunityId = ApexPages.currentPage().getParameters().get('id');
        oliDeleteList = new List<OpportunityLineItem>();
        mapRenamePackageName = new Map<String, String>();

        opportunity = [SELECT Id, Name, AccountId,Nombre_del_paciente_completo__c, Account.Name, CreatedDate, codigoDePresupuesto__c, procedimiento__c,
                       Porcentaje_de_Descuento__c, Tipo_de_cambio__c, CurrencyIsoCode, Precios_visibles__c, idioma__c,
                       Incluir__c, Vista__c, Nivel_de_descuento__c, Nivel_de_descuento__r.Porcentaje_de_Descuento__c,
                       fechaProbableParto__c, medicoTratante__c, medicoTratante__r.Name, Amount_Currency__c,especialidad__c, 
                       porcentajeDescuentoFormula__c, precioAproximadoCirugiaIVA__c, Otros_Honorarios__c, Otros_Insumos__c,
                       Otros_Medicamentos__c, Otros_Servicios__c, Description, medicoTratante__r.especialidad__c, 
                       medicoTratante__r.codigo_De_Profesional__c, Owner.Name, Nivel_de_descuento__r.Name,
                       asistente1__r.Name,asistente2__r.Name, anaestesiologo__r.Name, Extra_Discount__c,Tipo_de_Oportunidad__c,Show_Convenio__c,
                       Approved_Discount__c,PriceBook2Id,  Impuesto_al_Valor_Agregado_IVA__c,PrecioAproximado_de_la_Cirugia__c,Precio_aproximado_de_la_Cirugia_con_Desc__c,
                       Precio_aproximado_de_la_Cirugia_IVA__c
                       FROM Opportunity
                       WHERE Id =: opportunityId];
        	IdListaPrecios= opportunity.PriceBook2Id;
        checkActivoDescuento = opportunity.Approved_Discount__c;
        valorDescuentoActual = opportunity.Extra_Discount__c;
        
        for(Rename_Package__c renamePackageRec : [ select name, New_Name__c from Rename_Package__c where Opportunity__c =:opportunity.Id])
        {
            mapRenamePackageName.put(renamePackageRec.Name,renamePackageRec.New_Name__c);
        }

        language = opportunity.idioma__c;
        
        if(opportunity.Tipo_de_cambio__c == null)
            opportunity.Tipo_de_cambio__c = Decimal.valueOf(System.Label.tipoCambio);
        
        if(opportunity.Nivel_de_descuento__c != null)
            nivelDescuento = opportunity.Nivel_de_descuento__c + '~~~~~' + opportunity.Nivel_de_descuento__r.Porcentaje_de_Descuento__c;
        
        if(opportunity.Vista__c == 'Cliente')
            showChildProduct = false;
        else
            showChildProduct = true;
                    
        account = [SELECT Id, Name, IDCard__c, Phone, Email__c, Convenio__c
                   FROM Account
                   WHERE Id =: opportunity.AccountId];
                   
        contact = new Contact();
        
        paqueteList = new List<SelectOption>();
        paqueteList.add(new SelectOption('', '--Select Paquetes--'));
        for(paquete__c rec : [SELECT Id, Name FROM paquete__c WHERE Tipo__c = 'Paquetes'])
        {
            paqueteList.add(new SelectOption(rec.Id, rec.Name));
        }
        
        nivelDescuentoList = new List<SelectOption>();
        nivelDescuentoList.add(new SelectOption('', '--Select Niveles--'));
        for(nivelDescuento__c rec : [SELECT Id, Name, Porcentaje_de_Descuento__c
                                     FROM nivelDescuento__c 
                                     WHERE Visible_en_Cotizador__c = true])
        {
            nivelDescuentoList.add(new SelectOption(rec.Id + '~~~~~' + rec.Porcentaje_de_Descuento__c, rec.Name));
        }
        
        if(opportunity.procedimiento__c != null)
        paqueteRecord = [select precioAproxPartoNaturalAnestesiaMed__c, PrecioAproxPartoNatHabAnestesiaMed__c,precioAproxPartoCesareaMed__c,
            precioAproxPartoCesareaSalpIngMed__c, precioAproxPartoNaturalAnestesiaHM__c, PrecioAproxPartoNatHabAnestesiaHM__c,
            precioAproxPartoCesareaHM__c, precioAproxPartoCesareaSalpIngHM__c from paquete__c where Name =: opportunity.procedimiento__c limit 1];
        currencyList = new List<SelectOption>();
        currencyList.add(new SelectOption('', '--Select Currency--'));
        currencyList.add(new SelectOption('CRC', 'CRC - Costa Rica Colon'));
        currencyList.add(new SelectOption('USD', 'USD - U.S. Dollar'));
        
        productTypeList = new List<SelectOption>();
        productTypeList.add(new SelectOption('All', 'All'));
        productTypeList.add(new SelectOption('Servicios', 'Servicios'));
        productTypeList.add(new SelectOption('Insumos', 'Insumos'));
        productTypeList.add(new SelectOption('Medicamentos', 'Medicamentos'));
        
        oliServiceList = new List<ProductWrapper>();
        oliServiceOtrosList = new List<ProductWrapper>();
        oliInsumosList = new List<ProductWrapper>();
        oliInsumosOtrosList = new List<ProductWrapper>();
        oliMedicamentosList = new List<ProductWrapper>();
        oliMedicamentosOtrosList = new List<ProductWrapper>();
        oliHonorariosList = new List<ProductWrapper>();
        oliHonorariosOtrosList = new List<ProductWrapper>();
        oliOtrosList = new List<ProductWrapper>();
        oliExternalList = new List<ProductWrapper>();
        
        oliServiceListPDF = new List<ProductWrapper>();
        oliServiceOtrosListPDF = new List<ProductWrapper>();
        oliInsumosListPDF = new List<ProductWrapper>();
        oliInsumosOtrosListPDF = new List<ProductWrapper>();
        oliMedicamentosListPDF = new List<ProductWrapper>();
        oliMedicamentosOtrosListPDF = new List<ProductWrapper>();
        oliHonorariosListPDF = new List<ProductWrapper>();
        oliHonorariosOtrosListPDF = new List<ProductWrapper>();
        oliOtrosListPDF = new List<ProductWrapper>();
        oliExternalListPDF = new List<ProductWrapper>();

        mapSubPackagePackageAmount = new Map<String, Decimal>();
        mapProductNameAmount = new Map<String, Decimal>();
        mapPackageNameAmount = new Map<String, Decimal>();
        mapMainDiscountAmount = new Map<String, Decimal>();

        queryExistingProducts();
        queryContact();
    }
    
    public String contactId{get;set;}
    public void queryContact()
    {
        contact = new Contact();
        List<Contact> contactList = new List<Contact>();
       System.Debug('contactId--' + contactId + '--contactId');
        if(contactId != null)
            contactList = [SELECT Id, especialidad__c, Name
                                     FROM Contact
                                     WHERE Id =: contactId];
        if(contactList.size() == 0 )
            contactList = [SELECT Id, especialidad__c, Name FROM Contact WHERE Id =:opportunity.medicoTratante__c];
        if(contactList.size() > 0)
        {
            contact = contactList[0];
            opportunity.medicoTratante__c = contact.Id;
        }
    }
    
    public integer deleteIndx {get;set;}
    public String tipo {get;set;}
    public void deleteLineItems()
    {
        List<ProductWrapper> tempList = new List<ProductWrapper>();
        
        if(tipo == 'Servicios')
        {
            oliServiceList = deleteUtil(oliServiceList);
        }
        else if(tipo == 'Insumos')
        {
            oliInsumosList = deleteUtil(oliInsumosList);
        }
        else if(tipo == 'OtrosInsumos')
        {
            oliInsumosOtrosList = deleteUtil(oliInsumosOtrosList);
        }
        else if(tipo == 'Medicamentos')
        {
            oliMedicamentosList = deleteUtil(oliMedicamentosList);
        }
        else if(tipo == 'OtrosMedicamentos')
        {
            
            oliMedicamentosOtrosList = deleteUtil(oliMedicamentosOtrosList);
        }
        else if(tipo == 'OtrosServicios')
        {
            system.debug('Entro eliminar');
            oliServiceOtrosList = deleteUtil(oliServiceOtrosList);
        }
        
        else if(tipo == 'Honorarios')
        {
            oliHonorariosList = deleteUtil(oliHonorariosList);
        }
        else if(tipo == 'External')
        {
            oliExternalList = deleteUtil(oliExternalList);
        }
        else
        {
            oliOtrosList = deleteUtil(oliOtrosList);
        }
        calcTotal();
    }
    
    public List<ProductWrapper> deleteUtil(List<ProductWrapper> prList)
    {
        List<ProductWrapper> tempList = new List<ProductWrapper>();
        boolean deletePackage = false;
        for(integer i=0; i < prList.size(); i++)
        {
            if(deletePackage && prList[i].oli.Not_For_Create__c)
            {
                deletePackage = false;
                tempList.add(prList[i]);
            }
            else if(deletePackage)
            {
                if(prList[i].oli.Id != null)
                    oliDeleteList.add(prList[i].oli);
            }
            else if(deleteIndx == i)
            {
                if(prList[i].oli.Not_For_Create__c)
                    deletePackage = true; 
                if(prList[i].oli.Id != null) 
                    oliDeleteList.add(prList[i].oli);
            }
            else
            {
                tempList.add(prList[i]);
            }
        }
        
        return tempList;
    }
    
    public void queryExistingProducts()
    {
        List<OpportunityLineItem> oliList = [select id, PriceBookEntryId,Descripcion_Custom__c, PriceBookEntry.Product2Id, PriceBookEntry.Product2.Name, Original_Amount__c,
                                             Quantity, TotalPrice, UnitPrice, PriceBookEntry.Product2.Tipo__c, Not_For_Create__c, Child_Product__c,
                                             Product2.ProductCode,Product2.iva__c,Product2.margenDeTipoDescuento__c, PriceBookEntry.Product2.ProductCode, IVA__c, Part_of_Package__c, Category__c,
                                             Location_Index_Product__c, Otros__c,Parent_Package_Code__c,Main_Parent_Package_Code__c,PriceBookEntry.Product2.Aplica_descuento__c,
                                             OpportunityId
                                             from OpportunityLineItem
                                             where OpportunityId =: opportunityId];
                                    
        List<Opportunity_Package__c> oliPackageListList = [select id,Name, Opportunity__c, Original_Amount__c, Otros__c,
                                                           Quantity__c,Descripcion_Custom__c, Total_Price__c, Category__c, ProductCode__c, IVA__c, Location_Index_Package__c
                                                           from Opportunity_Package__c
                                                           where Opportunity__c =: opportunityId];
        
        List<ProductWrapper> oliServiceListtemp = new ProductWrapper[oliList.size() + oliPackageListList.size()];
        List<ProductWrapper> oliOtrosServiceListtemp = new ProductWrapper[oliList.size() + oliPackageListList.size()];
        List<ProductWrapper> oliInsumosListTemp = new ProductWrapper[oliList.size() + oliPackageListList.size()];
        List<ProductWrapper> oliOtrosInsumosListtemp = new ProductWrapper[oliList.size() + oliPackageListList.size()];
        List<ProductWrapper> oliMedicamentosListTemp = new ProductWrapper[oliList.size() + oliPackageListList.size()];
        List<ProductWrapper> oliOtrosMedicamentosListTemp = new ProductWrapper[oliList.size() + oliPackageListList.size()];
        List<ProductWrapper> oliHonorariosListTemp = new ProductWrapper[oliList.size() + oliPackageListList.size()];
        List<ProductWrapper> oliOtrosHonorariosListTemp = new ProductWrapper[oliList.size() + oliPackageListList.size()];
        List<ProductWrapper> oliOtrosListTemp = new ProductWrapper[oliList.size() + oliPackageListList.size()];
        List<ProductWrapper> oliExternalListtemp = new ProductWrapper[oliList.size() + oliPackageListList.size()];

        List<ProductWrapper> oliServiceListPDFTemp = new ProductWrapper[oliList.size() + oliPackageListList.size()];
        List<ProductWrapper> oliOtrosServiceListPDFTemp = new ProductWrapper[oliList.size() + oliPackageListList.size()];
        List<ProductWrapper> oliInsumosListPDFTemp = new ProductWrapper[oliList.size() + oliPackageListList.size()];
        List<ProductWrapper> oliOtrosInsumosListPDFTemp = new ProductWrapper[oliList.size() + oliPackageListList.size()];
        List<ProductWrapper> oliMedicamentosListPDFTemp = new ProductWrapper[oliList.size() + oliPackageListList.size()];
        List<ProductWrapper> oliOtrosMedicamentosListPDFTemp = new ProductWrapper[oliList.size() + oliPackageListList.size()];
        List<ProductWrapper> oliHonorariosListPDFTemp = new ProductWrapper[oliList.size() + oliPackageListList.size()];
        List<ProductWrapper> oliOtrosHonorariosListPDFTemp = new ProductWrapper[oliList.size() + oliPackageListList.size()];
        List<ProductWrapper> oliOtrosListPDFTemp = new ProductWrapper[oliList.size() + oliPackageListList.size()];
        List<ProductWrapper> oliExternalListPDFTemp = new ProductWrapper[oliList.size() + oliPackageListList.size()];

        if(oliList.size() > 0)
        {
            for(OpportunityLineItem oli : oliList)
            {
                Product2 product = new Product2(Name =oli.Descripcion_Custom__c, ProductCode = oli.PriceBookEntry.Product2.ProductCode);
                product.Aplica_descuento__c =  oli.PriceBookEntry.Product2.Aplica_descuento__c;
                product.iva__c = (oli.Product2.iva__c == null ? 0 : oli.Product2.iva__c);
                system.debug('Margen: '+oli.Product2.margenDeTipoDescuento__c);
                product.margenDeTipoDescuento__c = oli.Product2.margenDeTipoDescuento__c == NULL ? 0 : oli.Product2.margenDeTipoDescuento__c;
                system.debug('Margen Salida: '+product.margenDeTipoDescuento__c);
                if(oli.Category__c == 'OTROS SERVICIOS' && oli.Otros__c)
                {
                    if(!(showChildProduct == false && oli.Child_Product__c == true))
                        oliOtrosServiceListPDFTemp.add(Integer.ValueOf(oli.Location_Index_Product__c), new ProductWrapper(oli, product));
                    oliOtrosServiceListTemp.add(Integer.ValueOf(oli.Location_Index_Product__c), new ProductWrapper(oli, product));
                }
                else if(oli.Category__c == 'SERVICIOS HOSPITALARIOS')
                {
                    if(!(showChildProduct == false && oli.Child_Product__c == true))
                        oliServiceListPDFTemp.add(Integer.ValueOf(oli.Location_Index_Product__c), new ProductWrapper(oli, product));
                    oliServiceListTemp.add(Integer.ValueOf(oli.Location_Index_Product__c), new ProductWrapper(oli, product));
                }
                else if(oli.Category__c == 'OTROS INSUMOS' && oli.Otros__c)
                {
                    if(!(showChildProduct == false && oli.Child_Product__c == true))
                        oliOtrosInsumosListPDFTemp.add(Integer.ValueOf(oli.Location_Index_Product__c), new ProductWrapper(oli, product));
                    oliOtrosInsumosListTemp.add(Integer.ValueOf(oli.Location_Index_Product__c), new ProductWrapper(oli, product));
                }
                else if(oli.Category__c == 'INSUMOS HOSPITALARIOS')
                {
                    if(!(showChildProduct == false && oli.Child_Product__c == true))
                        oliInsumosListPDFTemp.add(Integer.ValueOf(oli.Location_Index_Product__c), new ProductWrapper(oli, product));
                    oliInsumosListTemp.add(Integer.ValueOf(oli.Location_Index_Product__c), new ProductWrapper(oli, product));
                }
                else if(oli.Category__c == 'OTROS MEDICAMENTOS' &&  oli.Otros__c)
                {
                    if(!(showChildProduct == false && oli.Child_Product__c == true))
                        oliOtrosMedicamentosListPDFTemp.add(Integer.ValueOf(oli.Location_Index_Product__c), new ProductWrapper(oli, product));
                    oliOtrosMedicamentosListTemp.add(Integer.ValueOf(oli.Location_Index_Product__c), new ProductWrapper(oli, product));
                }
                else if(oli.Category__c == 'MEDICAMENTOS HOSPITALARIOS' &&  oli.Part_of_Package__c)
                {
                    if(!(showChildProduct == false && oli.Child_Product__c == true))
                        oliMedicamentosListPDFTemp.add(Integer.ValueOf(oli.Location_Index_Product__c), new ProductWrapper(oli, product));
                    oliMedicamentosListTemp.add(Integer.ValueOf(oli.Location_Index_Product__c), new ProductWrapper(oli, product));
                }
                else if(oli.Category__c == 'OTROS HONORARIOS' &&  oli.Otros__c)
                {
                    if(!(showChildProduct == false && oli.Child_Product__c == true))
                        oliOtrosHonorariosListPDFTemp.add(Integer.ValueOf(oli.Location_Index_Product__c), new ProductWrapper(oli, product));
                    oliOtrosHonorariosListTemp.add(Integer.ValueOf(oli.Location_Index_Product__c), new ProductWrapper(oli, product));
                }  
                else if(oli.Category__c == 'HONORARIOS HOSPITALARIOS')
                {
                    if(!(showChildProduct == false && oli.Child_Product__c == true))
                        oliHonorariosListPDFTemp.add(Integer.ValueOf(oli.Location_Index_Product__c), new ProductWrapper(oli, product));
                    oliHonorariosListTemp.add(Integer.ValueOf(oli.Location_Index_Product__c), new ProductWrapper(oli, product));
                }                  
                else if(oli.Category__c == 'OTROS MEDICAMENTOS' && !oli.Part_of_Package__c)
                {
                    if(!(showChildProduct == false && oli.Child_Product__c == true))
                        oliOtrosMedicamentosListPDFTemp.add(Integer.ValueOf(oli.Location_Index_Product__c), new ProductWrapper(oli, product));
                    oliOtrosMedicamentosListTemp.add(Integer.ValueOf(oli.Location_Index_Product__c), new ProductWrapper(oli, product));
                }
                else if(oli.Category__c == 'EXTERNAL HOSPITALARIOS')
                {
                    if(!(showChildProduct == false && oli.Child_Product__c == true))
                        oliExternalListPDFTemp.add(Integer.ValueOf(oli.Location_Index_Product__c), new ProductWrapper(oli, product));
                    oliExternalListTemp.add(Integer.ValueOf(oli.Location_Index_Product__c), new ProductWrapper(oli, product));
                }
                else
                {
                    if(!(showChildProduct == false && oli.Child_Product__c == true))
                    oliOtrosListPDFTemp.add(Integer.ValueOf(oli.Location_Index_Product__c), new ProductWrapper(oli, product));
                    oliOtrosListTemp.add(Integer.ValueOf(oli.Location_Index_Product__c), new ProductWrapper(oli, product));   
                }
            }
        }

        if(oliPackageListList.size() > 0)
        {
            for(Opportunity_Package__c oliP : oliPackageListList)
            {
                Product2 product = new Product2(Name = oliP.Name, ProductCode = oliP.ProductCode__c);
                OpportunityLineItem oli = new OpportunityLineItem();
                oli.Category__c = olip.Category__c;
                oli.Descripcion_Custom__c = olip.Descripcion_Custom__c;
                oli.OpportunityId = oliP.Opportunity__c;
                oli.TotalPrice = oliP.Total_Price__c;
                oli.Original_Amount__c = oliP.Original_Amount__c;
                oli.Quantity = oliP.Quantity__c;
                oli.Not_For_Create__c = true;
                oli.IVA__c = oliP.IVA__c;
                oli.Location_Index_Product__c = oliP.Location_Index_Package__c;
                
                if(oliP.Category__c == 'OTROS SERVICIOS' && olip.Otros__c)// && showChildProduct)
                {
                    oliOtrosServiceListPDFTemp.add( Integer.ValueOf(oliP.Location_Index_Package__c), new ProductWrapper(oli, product));
                    oliOtrosServiceListTemp.add( Integer.ValueOf(oliP.Location_Index_Package__c), new ProductWrapper(oli, product));
                }
                if(oliP.Category__c == 'SERVICIOS HOSPITALARIOS')// && showChildProduct)
                {
                    oliServiceListPDFTemp.add( Integer.ValueOf(oliP.Location_Index_Package__c), new ProductWrapper(oli, product));
                    oliServiceListTemp.add( Integer.ValueOf(oliP.Location_Index_Package__c), new ProductWrapper(oli, product));
                }
                else if(oliP.Category__c == 'OTROS INSUMOS' && olip.Otros__c)// && showChildProduct)
                {
                    oliOtrosInsumosListPDFTemp.add(Integer.ValueOf(oliP.Location_Index_Package__c), new ProductWrapper(oli, product));
                    oliOtrosInsumosListTemp.add( Integer.ValueOf(oliP.Location_Index_Package__c), new ProductWrapper(oli, product));
                }
                else if(oliP.Category__c == 'INSUMOS HOSPITALARIOS')// && showChildProduct)
                {
                    oliInsumosListPDFTemp.add(Integer.ValueOf(oliP.Location_Index_Package__c), new ProductWrapper(oli, product));
                    oliInsumosListTemp.add( Integer.ValueOf(oliP.Location_Index_Package__c), new ProductWrapper(oli, product));
                }
                else if(oliP.Category__c == 'OTROS MEDICAMENTOS' && olip.Otros__c)//&& showChildProduct)
                {
                    oliOtrosMedicamentosListPDFTemp.add( Integer.ValueOf(oliP.Location_Index_Package__c), new ProductWrapper(oli, product));
                    oliOtrosMedicamentosListTemp.add( Integer.ValueOf(oliP.Location_Index_Package__c), new ProductWrapper(oli, product));
                }
                else if(oliP.Category__c == 'MEDICAMENTOS HOSPITALARIOS' )//&& showChildProduct)
                {
                    oliMedicamentosListPDFTemp.add( Integer.ValueOf(oliP.Location_Index_Package__c), new ProductWrapper(oli, product));
                    oliMedicamentosListTemp.add( Integer.ValueOf(oliP.Location_Index_Package__c), new ProductWrapper(oli, product));
                }
                else if(oliP.Category__c == 'OTROS HONORARIOS' && olip.Otros__c)// && showChildProduct)
                {
                    oliOtrosHonorariosListPDFTemp.add( Integer.ValueOf(oliP.Location_Index_Package__c), new ProductWrapper(oli, product));
                    oliOtrosHonorariosListTemp.add( Integer.ValueOf(oliP.Location_Index_Package__c), new ProductWrapper(oli, product));
                }
                else if(oliP.Category__c == 'EXTERNAL HOSPITALARIOS' )//&& showChildProduct)
                {
                    oliExternalListPDFTemp.add( Integer.ValueOf(oliP.Location_Index_Package__c), new ProductWrapper(oli, product));
                    oliExternalListTemp.add( Integer.ValueOf(oliP.Location_Index_Package__c), new ProductWrapper(oli, product));
                } 
                else if(oliP.Category__c == 'HONORARIOS HOSPITALARIOS')// && showChildProduct)
                {
                    oliHonorariosListPDFTemp.add( Integer.ValueOf(oliP.Location_Index_Package__c), new ProductWrapper(oli, product));
                    oliHonorariosListTemp.add( Integer.ValueOf(oliP.Location_Index_Package__c), new ProductWrapper(oli, product));
                }                  
                else if(oliP.Category__c == 'OTROS MEDICAMENTOS')// && showChildProduct)
                {
                    oliOtrosMedicamentosListPDFTemp.add( Integer.ValueOf(oliP.Location_Index_Package__c), new ProductWrapper(oli, product));
                    oliOtrosMedicamentosListTemp.add( Integer.ValueOf(oliP.Location_Index_Package__c), new ProductWrapper(oli, product));
                }
                else
                {
                    oliOtrosListPDFTemp.add( Integer.ValueOf(oliP.Location_Index_Package__c), new ProductWrapper(oli, product));
                    oliOtrosListTemp.add( Integer.ValueOf(oliP.Location_Index_Package__c), new ProductWrapper(oli, product));
                }
            }
        }

        for(ProductWrapper pw : oliOtrosServiceListPDFTemp)
        {
            if(pw != null)
                oliServiceOtrosListPDF.add(pw);
        }
        
        for(ProductWrapper pw : oliServiceListPDFTemp)
        {
            if(pw != null)
                oliServiceListPDF.add(pw);
        }
        
        for(ProductWrapper pw : oliOtrosServiceListTemp)
        {
            if(pw != null)
            oliServiceOtrosList.add(pw);
        }

        for(ProductWrapper pw : oliServiceListTemp)
        {
            if(pw != null)
            oliServiceList.add(pw);
        }
        
        for(ProductWrapper pw : oliOtrosInsumosListPDFTemp)
        {
            if(pw != null)
            oliInsumosOtrosListPDF.add(pw);
        }

        for(ProductWrapper pw : oliInsumosListPDFTemp)
        {
            if(pw != null)
            oliInsumosListPDF.add(pw);
        }
        
        for(ProductWrapper pw : oliOtrosInsumosListTemp)
        {
            if(pw != null)
            oliInsumosOtrosList.add(pw);
        }

        for(ProductWrapper pw : oliInsumosListTemp)
        {
            if(pw != null)
            oliInsumosList.add(pw);
        }
        
        for(ProductWrapper pw : oliOtrosMedicamentosListPDFTemp)
        {
            if(pw != null)
            oliMedicamentosOtrosListPDF.add(pw);
        }

        for(ProductWrapper pw : oliMedicamentosListPDFTemp)
        {
            if(pw != null)
            oliMedicamentosListPDF.add(pw);
        }
        
        for(ProductWrapper pw : oliOtrosMedicamentosListTemp)
        {
            if(pw != null)
            oliMedicamentosOtrosList.add(pw);
        }

        for(ProductWrapper pw : oliMedicamentosListTemp)
        {
            if(pw != null)
            oliMedicamentosList.add(pw);
        }
        
        for(ProductWrapper pw : oliExternalListPDFTemp)
        {
            if(pw != null)
            oliExternalListPDF.add(pw);
        }

        for(ProductWrapper pw : oliExternalListTemp)
        {
            if(pw != null)
            oliExternalList.add(pw);
        }

        for(ProductWrapper pw : oliOtrosHonorariosListPDFTemp)
        {
            if(pw != null)
            oliHonorariosOtrosListPDF.add(pw);
        }

        for(ProductWrapper pw : oliHonorariosListPDFTemp)
        {
            if(pw != null)
            oliHonorariosListPDF.add(pw);
        }
        
        for(ProductWrapper pw : oliOtrosHonorariosListTemp)
        {
            if(pw != null)
            oliHonorariosOtrosList.add(pw);
        }

        for(ProductWrapper pw : oliHonorariosListTemp)
        {
            if(pw != null)
            oliHonorariosList.add(pw);
        }

 

        for(ProductWrapper pw : oliOtrosListPDFTemp)
        {
            if(pw != null)
            oliOtrosListPDF.add(pw);
        }

        for(ProductWrapper pw : oliOtrosListTemp)
        {
            if(pw != null)
            oliOtrosList.add(pw);
        }

        calcTotal();
    }
    
    public void changeCurrency()
    {
        changeCurrencyUtility(oliServiceList);
        
        changeCurrencyUtility(oliInsumosList);
        
        changeCurrencyUtility(oliMedicamentosList);

        changeCurrencyUtility(oliMedicamentosOtrosList);
        
        changeCurrencyUtility(oliServiceOtrosList);
        
        changeCurrencyUtility(oliInsumosOtrosList);
        
        changeCurrencyUtility(oliHonorariosOtrosList);
        
        changeCurrencyUtility(oliHonorariosList);

        changeCurrencyUtility(oliOtrosList);

        changeCurrencyUtility(oliExternalList);
        
        calcTotal();
    }
    
    public void changeCurrencyUtility(List<ProductWrapper> oliList)
    {
        for(ProductWrapper w : oliList)
        {
            if(w.oli.TotalPrice != null && opportunity.Amount_Currency__c == 'CRC')
                w.oli.TotalPrice = w.oli.TotalPrice * opportunity.Tipo_de_cambio__c;
            else if(w.oli.TotalPrice != null && opportunity.Amount_Currency__c == 'USD')
                w.oli.TotalPrice = (w.oli.TotalPrice / opportunity.Tipo_de_cambio__c);
        }
    }
    
    public void calcTotal()
    {
        discountName = null;
        if(opportunity.Nivel_de_descuento__c != null && opportunity.Nivel_de_descuento__r.Name != null)
        {
            discountName = opportunity.Nivel_de_descuento__r.Name;
        }
        else
        {
            for(SelectOption so : nivelDescuentoList)
            {
                if(so.getValue() == nivelDescuento)
                {
                    discountName = so.getLabel();
                }
            }
        }
        changeDiscountDropDown(discountName);
        
        
        otrosIVATotal = otrosTotal = serviceTotal = serviceIVATotal = insumosTotal = insumosIVATotal = 
        medicamentosTotal = medicamentosIVATotal = medicamentosOtrosTotal = medicamentosOtrosIVATotal = 
        honorariosTotal = honorariosIVATotal = serviceOtrosTotal = insumosOtrosTotal = honorariosOtrosTotal = 
        serviceOtrosIVATotal = insumosOtrosIVATotal = honorariosOtrosIVATotal = externalTotal = externalIVATotal = 0;
        serviceTotalSINDESCUENTO = serviceOtrosTotalSINDESCUENTO = insumosTotalSINDESCUENTO =
            insumosOtrosTotalSINDESCUENTO = medicamentosTotalSINDESCUENTO = medicamentosOtrosTotalSINDESCUENTO =
            honorariosTotalSINDESCUENTO = honorariosOtrosTotalSINDESCUENTO = otrosTotalSINDESCUENTO = externalTotalSINDESCUENTO =  0;
        Decimal serviceTotalNFCtrue = 0;
        //Decimal insumosTotalNFCtrue = 0;
        Decimal medicamentosTotalNFCtrue = 0;
        Decimal medicamentosOtrosTotalNFCtrue = 0;
        Decimal honorariosTotalNFCtrue = 0;
        Decimal otrosTotalNFCtrue = 0;
		
        //Part_of_Package__c - This will be true for all the packages and package product. It will be false for individual products.
        //Not_For_Create__c = This will be true for package when getting the data by making the query from the database..
        //It will be true for the packages when we add from the UI. But in database we store it false. 
        Map<String, Decimal> mapPackageNameTotalAmount = new Map<String, Decimal>();
        Map<String, Decimal> mapPackageNameTotalIva = new Map<String, Decimal>();
        for(ProductWrapper w : oliServiceList)
        {
            if(showChildProduct == false && w.oli.Child_Product__c)
                continue;
            if(w.oli.Original_Amount__c != null && w.oli.Quantity != null)
            {
                //This If condition will execute for the products which are part of the  packages.
                if(w.oli.Parent_Package_Code__c != null)
                {
                  decimal precioProducto = w.oli.Original_Amount__c + (ActivarMargen == true ? ( w.oli.Original_Amount__c * (((w.product.margenDeTipoDescuento__c==null ? 0 :w.product.margenDeTipoDescuento__c) )/100)):0);
       
                    if(w.product.Aplica_descuento__c)
                        
                        w.oli.TotalPrice = precioProducto * w.oli.Quantity * getApplicableDiscountPercentage(w.oli.Parent_Package_Code__c, w.product.Name);
                    	
                    else
                        w.oli.TotalPrice = precioProducto * w.oli.Quantity;
					
                    if(w.oli.TotalPrice != null)
                        w.oli.IVA__c = w.oli.TotalPrice  * (w.product.iva__c == null ? 0 :(w.product.iva__c/100));
                    
                    if(!mapPackageNameTotalAmount.containsKey(w.oli.Parent_Package_Code__c))
                    {
                        mapPackageNameTotalAmount.put(w.oli.Parent_Package_Code__c, w.oli.TotalPrice);
                    }
                    else
                    {
                        Decimal d1 = mapPackageNameTotalAmount.get(w.oli.Parent_Package_Code__c);
                        mapPackageNameTotalAmount.put(w.oli.Parent_Package_Code__c, w.oli.TotalPrice+d1);
                    }
                    
                    if(!mapPackageNameTotalIva.containsKey(w.oli.Parent_Package_Code__c))
                    {
                        mapPackageNameTotalIva.put(w.oli.Parent_Package_Code__c, w.oli.IVA__c);
                    }
                    else
                    {
                        Decimal d1 = mapPackageNameTotalIva.get(w.oli.Parent_Package_Code__c);
                        mapPackageNameTotalIva.put(w.oli.Parent_Package_Code__c, w.oli.IVA__c+d1);
                    }
                    
                }
                //This Else If condition will execute for the Individual products which are not part of the package.
                else if(w.oli.Parent_Package_Code__c == null && w.oli.Not_For_Create__c == false)
                {
                    decimal precioProducto = w.oli.Original_Amount__c + (ActivarMargen == true ? ( w.oli.Original_Amount__c * (((w.product.margenDeTipoDescuento__c==null ? 0 :w.product.margenDeTipoDescuento__c) )/100)):0);

                    if(w.product.Aplica_descuento__c)
                        w.oli.TotalPrice = precioProducto * w.oli.Quantity * getApplicableDiscountPercentage(w.oli.Main_Parent_Package_Code__c, w.product.Name);
                    else
                        w.oli.TotalPrice = precioProducto * w.oli.Quantity;
                    
                    if(w.oli.TotalPrice != null)
                    {
                        w.oli.IVA__c = w.oli.TotalPrice *  (w.product.iva__c == null ? 0 :(w.product.iva__c/100)) ;
                        serviceTotalSINDESCUENTO += (precioProducto * w.oli.Quantity);
                        serviceTotal += w.oli.TotalPrice;
                        serviceIVATotal += w.oli.IVA__c ;
                         
                    }
                }
            }
        }
        
        
        for(ProductWrapper w : oliServiceList)
        {
            if(showChildProduct == false && w.oli.Child_Product__c)
                continue;
            if(w.oli.Original_Amount__c != null && w.oli.Quantity != null)
            {
                //All Packages
                if(w.oli.Parent_Package_Code__c == null && w.oli.Not_For_Create__c == true)
                {                    
                    System.Debug(' *****ONly Package  test123455' + w.oli);
                    //This condition will execute if there is no product in the package.
                    if(mapPackageNameTotalAmount.get(w.product.ProductCode) == null)
                    {
                        decimal precioProducto = w.oli.Original_Amount__c + (ActivarMargen == true ? (w.oli.Original_Amount__c * (((w.product.margenDeTipoDescuento__c==null ? 0 :w.product.margenDeTipoDescuento__c) )/100)):0);

                        w.oli.TotalPrice = precioProducto * w.oli.Quantity * getApplicableDiscountPercentage(w.oli.Parent_Package_Code__c, null);
                        w.oli.IVA__c = w.oli.TotalPrice *  (w.product.iva__c == null ? 0 :(w.product.iva__c/100));
                        serviceTotalSINDESCUENTO  += (precioProducto * w.oli.Quantity);
                            serviceTotal += w.oli.TotalPrice;
                            serviceIVATotal += w.oli.IVA__c ;
                    }
                    //This else Condition will execute for All the Packages if that contains product.
                    else
                    {
                        
                        decimal precioProducto = mapPackageNameTotalAmount.get(w.product.ProductCode) + (ActivarMargen == true ? ( mapPackageNameTotalAmount.get(w.product.ProductCode) * (((w.product.margenDeTipoDescuento__c==null ? 0 :w.product.margenDeTipoDescuento__c) )/100)):0);
                        w.oli.TotalPrice = precioProducto  * w.oli.Quantity;
                        decimal montoServiceTotalSINDESCUENTO = precioProducto  * w.oli.Quantity;
                        
                        if(w.oli.TotalPrice != null)
                        {
                            w.oli.IVA__c = mapPackageNameTotalIva.get(w.product.ProductCode);
                            serviceTotalSINDESCUENTO += montoServiceTotalSINDESCUENTO;
                            serviceTotal += w.oli.TotalPrice;
                            serviceIVATotal += w.oli.IVA__c ;
                        }
                    }
                }
            }
        }
        mapPackageNameTotalAmount.clear();

        /******************/
        for(ProductWrapper w : oliServiceOtrosList)
        {
           if(showChildProduct == false && w.oli.Child_Product__c)
                continue;
            if(w.oli.Original_Amount__c != null && w.oli.Quantity != null)
            {
                //This If condition will execute for the products which are part of the  packages.
                if(w.oli.Parent_Package_Code__c != null)
                {
                       decimal precioProducto = w.oli.Original_Amount__c + (ActivarMargen == true ? ( w.oli.Original_Amount__c * (((w.product.margenDeTipoDescuento__c==null ? 0 :w.product.margenDeTipoDescuento__c) )/100)):0);
           
                    if(w.product.Aplica_descuento__c)
                        
                        w.oli.TotalPrice = precioProducto * w.oli.Quantity * getApplicableDiscountPercentage(w.oli.Parent_Package_Code__c, w.product.Name);
                    	
                    else
                        w.oli.TotalPrice = precioProducto * w.oli.Quantity;
					
                    if(w.oli.TotalPrice != null)
                        w.oli.IVA__c = w.oli.TotalPrice  * (w.product.iva__c == null ? 0 :(w.product.iva__c/100));
                    
                    if(!mapPackageNameTotalAmount.containsKey(w.oli.Parent_Package_Code__c))
                    {
                        mapPackageNameTotalAmount.put(w.oli.Parent_Package_Code__c, w.oli.TotalPrice);
                    }
                    else
                    {
                        Decimal d1 = mapPackageNameTotalAmount.get(w.oli.Parent_Package_Code__c);
                        mapPackageNameTotalAmount.put(w.oli.Parent_Package_Code__c, w.oli.TotalPrice+d1);
                    }
                    
                    if(!mapPackageNameTotalIva.containsKey(w.oli.Parent_Package_Code__c))
                    {
                        mapPackageNameTotalIva.put(w.oli.Parent_Package_Code__c, w.oli.IVA__c);
                    }
                    else
                    {
                        Decimal d1 = mapPackageNameTotalIva.get(w.oli.Parent_Package_Code__c);
                        mapPackageNameTotalIva.put(w.oli.Parent_Package_Code__c, w.oli.IVA__c+d1);
                    }
                    
                }
                //This Else If condition will execute for the Individual products which are not part of the package.
                else if(w.oli.Parent_Package_Code__c == null && w.oli.Not_For_Create__c == false)
                {
                    decimal precioProducto = w.oli.Original_Amount__c + (ActivarMargen == true ? ( w.oli.Original_Amount__c * (((w.product.margenDeTipoDescuento__c==null ? 0 :w.product.margenDeTipoDescuento__c) )/100)):0);

                    if(w.product.Aplica_descuento__c)
                        w.oli.TotalPrice = precioProducto * w.oli.Quantity * getApplicableDiscountPercentage(w.oli.Main_Parent_Package_Code__c, w.product.Name);
                    else
                        w.oli.TotalPrice = precioProducto * w.oli.Quantity;
                    
                    if(w.oli.TotalPrice != null)
                    {
                        w.oli.IVA__c = w.oli.TotalPrice *  (w.product.iva__c == null ? 0 :(w.product.iva__c/100)) ;
                        serviceOtrosTotalSINDESCUENTO += (precioProducto * w.oli.Quantity);
                        serviceOtrosTotal += w.oli.TotalPrice;
                        serviceOtrosIVATotal += w.oli.IVA__c ;
                    }
                }
            }
        }
        System.Debug(' mapPackageNameTotalAmount test123455' + mapPackageNameTotalAmount);
        for(ProductWrapper w : oliServiceOtrosList)
        {
             if(showChildProduct == false && w.oli.Child_Product__c)
                continue;
            if(w.oli.Original_Amount__c != null && w.oli.Quantity != null)
            {
                //All Packages
                if(w.oli.Parent_Package_Code__c == null && w.oli.Not_For_Create__c == true)
                {                    
                    System.Debug(' *****ONly Package  test123455' + w.oli);
                    //This condition will execute if there is no product in the package.
                    if(mapPackageNameTotalAmount.get(w.product.ProductCode) == null)
                    {
                        decimal precioProducto = w.oli.Original_Amount__c + (ActivarMargen == true ? ( w.oli.Original_Amount__c * (((w.product.margenDeTipoDescuento__c==null ? 0 :w.product.margenDeTipoDescuento__c) )/100)):0);
                        
                        w.oli.TotalPrice = precioProducto * w.oli.Quantity * getApplicableDiscountPercentage(w.oli.Parent_Package_Code__c, null);
                        w.oli.IVA__c = w.oli.TotalPrice *  (w.product.iva__c == null ? 0 :(w.product.iva__c/100));
                          serviceOtrosTotalSINDESCUENTO += (precioProducto * w.oli.Quantity);
                        serviceOtrosTotal += w.oli.TotalPrice;
                        serviceOtrosIVATotal += w.oli.IVA__c ;
                    }
                    //This else Condition will execute for All the Packages if that contains product.
                    else
                    {
                        
                        decimal precioProducto = mapPackageNameTotalAmount.get(w.product.ProductCode) + (ActivarMargen == true ? ( mapPackageNameTotalAmount.get(w.product.ProductCode) * (((w.product.margenDeTipoDescuento__c==null ? 0 :w.product.margenDeTipoDescuento__c) )/100)):0);
                        w.oli.TotalPrice = precioProducto  * w.oli.Quantity;
                        
                        if(w.oli.TotalPrice != null)
                        {
                            w.oli.IVA__c = mapPackageNameTotalIva.get(w.product.ProductCode);
                            serviceOtrosTotalSINDESCUENTO += (precioProducto * w.oli.Quantity);
                        serviceOtrosTotal += w.oli.TotalPrice;
                        serviceOtrosIVATotal += w.oli.IVA__c ;
                        }
                    }
                }
            }
        }
        mapPackageNameTotalAmount.clear();
        
        /******************/
        for(ProductWrapper w : oliInsumosList)
        {
            if(showChildProduct == false && w.oli.Child_Product__c)
                continue;
            if(w.oli.Original_Amount__c != null && w.oli.Quantity != null)
            {
                //This If condition will execute for the products which are part of the  packages.
                if(w.oli.Parent_Package_Code__c != null)
                {
                                  
                    if(w.product.Aplica_descuento__c)
                        
                        w.oli.TotalPrice = w.oli.Original_Amount__c * w.oli.Quantity * getApplicableDiscountPercentage(w.oli.Parent_Package_Code__c, w.product.Name);
                    	
                    else
                        w.oli.TotalPrice = w.oli.Original_Amount__c * w.oli.Quantity;
					
                    if(w.oli.TotalPrice != null)
                        w.oli.IVA__c = w.oli.TotalPrice  * (w.product.iva__c == null ? 0 :(w.product.iva__c/100));
                    
                    if(!mapPackageNameTotalAmount.containsKey(w.oli.Parent_Package_Code__c))
                    {
                        mapPackageNameTotalAmount.put(w.oli.Parent_Package_Code__c, w.oli.TotalPrice);
                    }
                    else
                    {
                        Decimal d1 = mapPackageNameTotalAmount.get(w.oli.Parent_Package_Code__c);
                        mapPackageNameTotalAmount.put(w.oli.Parent_Package_Code__c, w.oli.TotalPrice+d1);
                    }
                    
                    if(!mapPackageNameTotalIva.containsKey(w.oli.Parent_Package_Code__c))
                    {
                        mapPackageNameTotalIva.put(w.oli.Parent_Package_Code__c, w.oli.IVA__c);
                    }
                    else
                    {
                        Decimal d1 = mapPackageNameTotalIva.get(w.oli.Parent_Package_Code__c);
                        mapPackageNameTotalIva.put(w.oli.Parent_Package_Code__c, w.oli.IVA__c+d1);
                    }
                    
                }
                //This Else If condition will execute for the Individual products which are not part of the package.
                else if(w.oli.Parent_Package_Code__c == null && w.oli.Not_For_Create__c == false)
                {
                    decimal precioProducto = w.oli.Original_Amount__c + (ActivarMargen == true ? ( w.oli.Original_Amount__c * (((w.product.margenDeTipoDescuento__c==null ? 0 :w.product.margenDeTipoDescuento__c) )/100)):0);

                    if(w.product.Aplica_descuento__c)
                        w.oli.TotalPrice = precioProducto * w.oli.Quantity * getApplicableDiscountPercentage(w.oli.Main_Parent_Package_Code__c, w.product.Name);
                    else
                        w.oli.TotalPrice = precioProducto * w.oli.Quantity;
                    
                    if(w.oli.TotalPrice != null)
                    {
                        w.oli.IVA__c = w.oli.TotalPrice *  (w.product.iva__c == null ? 0 :(w.product.iva__c/100)) ;
                        insumosOtrosTotalSINDESCUENTO += (precioProducto * w.oli.Quantity);
                        insumosTotal += w.oli.TotalPrice;
                        insumosIVATotal += w.oli.IVA__c ;
                    }
                }
            }
        }
        System.Debug(' mapPackageNameTotalAmount test123455' + mapPackageNameTotalAmount);
        for(ProductWrapper w : oliInsumosList)
        {
            if(showChildProduct == false && w.oli.Child_Product__c)
                continue;
            if(w.oli.Original_Amount__c != null && w.oli.Quantity != null)
            {
                //All Packages
                if(w.oli.Parent_Package_Code__c == null && w.oli.Not_For_Create__c == true)
                {                    
                    System.Debug(' *****ONly Package  test123455' + w.oli);
                    //This condition will execute if there is no product in the package.
                    if(mapPackageNameTotalAmount.get(w.product.ProductCode) == null)
                    {
                        decimal precioProducto = w.oli.Original_Amount__c + (ActivarMargen == true ? ( w.oli.Original_Amount__c * (((w.product.margenDeTipoDescuento__c==null ? 0 :w.product.margenDeTipoDescuento__c) )/100)):0);
                       
                        w.oli.TotalPrice = precioProducto * w.oli.Quantity * getApplicableDiscountPercentage(w.oli.Parent_Package_Code__c, null);
                        w.oli.IVA__c = w.oli.TotalPrice *  (w.product.iva__c == null ? 0 :(w.product.iva__c/100));
                         insumosTotalSINDESCUENTO += (precioProducto * w.oli.Quantity);
                        insumosTotal += w.oli.TotalPrice;
                        insumosIVATotal += w.oli.IVA__c ;
                    }
                    //This else Condition will execute for All the Packages if that contains product.
                    else
                    {
                        
                        decimal precioProducto = mapPackageNameTotalAmount.get(w.product.ProductCode) + (ActivarMargen == true ? ( mapPackageNameTotalAmount.get(w.product.ProductCode) * (((w.product.margenDeTipoDescuento__c==null ? 0 :w.product.margenDeTipoDescuento__c) )/100)):0);
                        w.oli.TotalPrice = precioProducto  * w.oli.Quantity;
                        
                        if(w.oli.TotalPrice != null)
                        {
                            w.oli.IVA__c = mapPackageNameTotalIva.get(w.product.ProductCode);
                            insumosTotal += w.oli.TotalPrice;
                             insumosTotalSINDESCUENTO += w.oli.TotalPrice;
                             
                            insumosIVATotal += w.oli.IVA__c ;
                        }
                    }
                }
            }
        }
        mapPackageNameTotalAmount.clear();
        
       /******************/
        for(ProductWrapper w : oliInsumosOtrosList)
        {
            if(showChildProduct == false && w.oli.Child_Product__c)
                continue;
            if(w.oli.Original_Amount__c != null && w.oli.Quantity != null)
            {
                //This If condition will execute for the products which are part of the  packages.
                if(w.oli.Parent_Package_Code__c != null)
                {
                                  
                    if(w.product.Aplica_descuento__c)
                        
                        w.oli.TotalPrice = w.oli.Original_Amount__c * w.oli.Quantity * getApplicableDiscountPercentage(w.oli.Parent_Package_Code__c, w.product.Name);
                    	
                    else
                        w.oli.TotalPrice = w.oli.Original_Amount__c * w.oli.Quantity;
					
                    if(w.oli.TotalPrice != null)
                        w.oli.IVA__c = w.oli.TotalPrice  * (w.product.iva__c == null ? 0 :(w.product.iva__c/100));
                    
                    if(!mapPackageNameTotalAmount.containsKey(w.oli.Parent_Package_Code__c))
                    {
                        mapPackageNameTotalAmount.put(w.oli.Parent_Package_Code__c, w.oli.TotalPrice);
                    }
                    else
                    {
                        Decimal d1 = mapPackageNameTotalAmount.get(w.oli.Parent_Package_Code__c);
                        mapPackageNameTotalAmount.put(w.oli.Parent_Package_Code__c, w.oli.TotalPrice+d1);
                    }
                    
                    if(!mapPackageNameTotalIva.containsKey(w.oli.Parent_Package_Code__c))
                    {
                        mapPackageNameTotalIva.put(w.oli.Parent_Package_Code__c, w.oli.IVA__c);
                    }
                    else
                    {
                        Decimal d1 = mapPackageNameTotalIva.get(w.oli.Parent_Package_Code__c);
                        mapPackageNameTotalIva.put(w.oli.Parent_Package_Code__c, w.oli.IVA__c+d1);
                    }
                    
                }
                //This Else If condition will execute for the Individual products which are not part of the package.
                else if(w.oli.Parent_Package_Code__c == null && w.oli.Not_For_Create__c == false)
                {
                    decimal precioProducto = w.oli.Original_Amount__c + (ActivarMargen == true ? ( w.oli.Original_Amount__c * (((w.product.margenDeTipoDescuento__c==null ? 0 :w.product.margenDeTipoDescuento__c) )/100)):0);

                    if(w.product.Aplica_descuento__c)
                        w.oli.TotalPrice = precioProducto * w.oli.Quantity * getApplicableDiscountPercentage(w.oli.Main_Parent_Package_Code__c, w.product.Name);
                    else
                        w.oli.TotalPrice = precioProducto * w.oli.Quantity;
                    
                    if(w.oli.TotalPrice != null)
                    {
                        w.oli.IVA__c = w.oli.TotalPrice *  (w.product.iva__c == null ? 0 :(w.product.iva__c/100)) ;
                        insumosOtrosTotalSINDESCUENTO += (precioProducto * w.oli.Quantity);
                        insumosOtrosTotal += w.oli.TotalPrice;
                        insumosOtrosIVATotal += w.oli.IVA__c ;
                    }
                }
            }
        }
        System.Debug(' mapPackageNameTotalAmount test123455' + mapPackageNameTotalAmount);
        for(ProductWrapper w : oliInsumosOtrosList)
        {
            if(showChildProduct == false && w.oli.Child_Product__c)
                continue;
            if(w.oli.Original_Amount__c != null && w.oli.Quantity != null)
            {
                //All Packages
                if(w.oli.Parent_Package_Code__c == null && w.oli.Not_For_Create__c == true)
                {                    
                    System.Debug(' *****ONly Package  test123455' + w.oli);
                    //This condition will execute if there is no product in the package.
                    if(mapPackageNameTotalAmount.get(w.product.ProductCode) == null)
                    {decimal precioProducto = w.oli.Original_Amount__c + (ActivarMargen == true ? ( w.oli.Original_Amount__c * (((w.product.margenDeTipoDescuento__c==null ? 0 :w.product.margenDeTipoDescuento__c) )/100)):0);

                        w.oli.TotalPrice = precioProducto * w.oli.Quantity * getApplicableDiscountPercentage(w.oli.Parent_Package_Code__c, null);
                        w.oli.IVA__c = w.oli.TotalPrice *  (w.product.iva__c == null ? 0 :(w.product.iva__c/100));
                        insumosOtrosTotalSINDESCUENTO += (precioProducto * w.oli.Quantity);
                        insumosOtrosTotal += w.oli.TotalPrice;
                        insumosOtrosIVATotal += w.oli.IVA__c ;
                    }
                    //This else Condition will execute for All the Packages if that contains product.
                    else
                    {
                        
                        decimal precioProducto = mapPackageNameTotalAmount.get(w.product.ProductCode) + (ActivarMargen == true ? ( mapPackageNameTotalAmount.get(w.product.ProductCode) * (((w.product.margenDeTipoDescuento__c==null ? 0 :w.product.margenDeTipoDescuento__c) )/100)):0);
                        w.oli.TotalPrice = precioProducto  * w.oli.Quantity;
                        
                        if(w.oli.TotalPrice != null)
                        {
                            w.oli.IVA__c = mapPackageNameTotalIva.get(w.product.ProductCode);
                            insumosOtrosTotal += w.oli.TotalPrice;
                             insumosOtrosTotalSINDESCUENTO += w.oli.TotalPrice;
                            insumosOtrosIVATotal += w.oli.IVA__c ;
                        }
                    }
                }
            }
        }
        mapPackageNameTotalAmount.clear();

        /******************/
        for(ProductWrapper w : oliMedicamentosList)
        {
            if(showChildProduct == false && w.oli.Child_Product__c)
                continue;
            if(w.oli.Original_Amount__c != null && w.oli.Quantity != null)
            {
                //This If condition will execute for the products which are part of the  packages.
                if(w.oli.Parent_Package_Code__c != null)
                {
                                  
                    if(w.product.Aplica_descuento__c)
                        
                        w.oli.TotalPrice = w.oli.Original_Amount__c * w.oli.Quantity * getApplicableDiscountPercentage(w.oli.Parent_Package_Code__c, w.product.Name);
                    	
                    else
                        w.oli.TotalPrice = w.oli.Original_Amount__c * w.oli.Quantity;
					
                    if(w.oli.TotalPrice != null)
                        w.oli.IVA__c = w.oli.TotalPrice  * (w.product.iva__c == null ? 0 :(w.product.iva__c/100));
                    
                    if(!mapPackageNameTotalAmount.containsKey(w.oli.Parent_Package_Code__c))
                    {
                        mapPackageNameTotalAmount.put(w.oli.Parent_Package_Code__c, w.oli.TotalPrice);
                    }
                    else
                    {
                        Decimal d1 = mapPackageNameTotalAmount.get(w.oli.Parent_Package_Code__c);
                        mapPackageNameTotalAmount.put(w.oli.Parent_Package_Code__c, w.oli.TotalPrice+d1);
                    }
                    
                    if(!mapPackageNameTotalIva.containsKey(w.oli.Parent_Package_Code__c))
                    {
                        mapPackageNameTotalIva.put(w.oli.Parent_Package_Code__c, w.oli.IVA__c);
                    }
                    else
                    {
                        Decimal d1 = mapPackageNameTotalIva.get(w.oli.Parent_Package_Code__c);
                        mapPackageNameTotalIva.put(w.oli.Parent_Package_Code__c, w.oli.IVA__c+d1);
                    }
                    
                }
                //This Else If condition will execute for the Individual products which are not part of the package.
                else if(w.oli.Parent_Package_Code__c == null && w.oli.Not_For_Create__c == false)
                {
                    decimal precioProducto = w.oli.Original_Amount__c + (ActivarMargen == true ? ( w.oli.Original_Amount__c * (((w.product.margenDeTipoDescuento__c==null ? 0 :w.product.margenDeTipoDescuento__c) )/100)):0);

                    if(w.product.Aplica_descuento__c)
                        w.oli.TotalPrice = precioProducto * w.oli.Quantity * getApplicableDiscountPercentage(w.oli.Main_Parent_Package_Code__c, w.product.Name);
                    else
                        w.oli.TotalPrice = precioProducto * w.oli.Quantity;
                    
                    if(w.oli.TotalPrice != null)
                    {
                        w.oli.IVA__c = w.oli.TotalPrice *  (w.product.iva__c == null ? 0 :(w.product.iva__c/100)) ;
                        medicamentosTotalSINDESCUENTO += (precioProducto * w.oli.Quantity);
                        medicamentosTotal += w.oli.TotalPrice;
                        medicamentosIVATotal += w.oli.IVA__c ;
                    }
                }
            }
        }
        System.Debug(' mapPackageNameTotalAmount test123455' + mapPackageNameTotalAmount);
        for(ProductWrapper w : oliMedicamentosList)
        {
            if(showChildProduct == false && w.oli.Child_Product__c)
                continue;
            if(w.oli.Original_Amount__c != null && w.oli.Quantity != null)
            {
                //All Packages
                if(w.oli.Parent_Package_Code__c == null && w.oli.Not_For_Create__c == true)
                {                    
                    System.Debug(' *****ONly Package  test123455' + w.oli);
                    //This condition will execute if there is no product in the package.
                    if(mapPackageNameTotalAmount.get(w.product.ProductCode) == null)
                    {
                        decimal precioProducto = w.oli.Original_Amount__c + (ActivarMargen == true ? ( w.oli.Original_Amount__c * (((w.product.margenDeTipoDescuento__c==null ? 0 :w.product.margenDeTipoDescuento__c) )/100)):0);
                        
                        w.oli.TotalPrice = precioProducto * w.oli.Quantity * getApplicableDiscountPercentage(w.oli.Parent_Package_Code__c, null);
                        w.oli.IVA__c = w.oli.TotalPrice *  (w.product.iva__c == null ? 0 :(w.product.iva__c/100));
                         medicamentosTotalSINDESCUENTO += (precioProducto * w.oli.Quantity);
                        medicamentosTotal += w.oli.TotalPrice;
                        medicamentosIVATotal += w.oli.IVA__c ;
                        
                         
                    }
                    //This else Condition will execute for All the Packages if that contains product.
                    else
                    {
                        
                        decimal precioProducto = mapPackageNameTotalAmount.get(w.product.ProductCode) + (ActivarMargen == true ? ( mapPackageNameTotalAmount.get(w.product.ProductCode) * (((w.product.margenDeTipoDescuento__c==null ? 0 :w.product.margenDeTipoDescuento__c) )/100)):0);
                        w.oli.TotalPrice = precioProducto  * w.oli.Quantity;
                        
                        if(w.oli.TotalPrice != null)
                        {
                            w.oli.IVA__c = mapPackageNameTotalIva.get(w.product.ProductCode);
                            medicamentosTotal += w.oli.TotalPrice;
                             medicamentosTotalSINDESCUENTO += w.oli.TotalPrice;
                             
                            medicamentosIVATotal += w.oli.IVA__c ;
                        }
                    }
                }
            }
        }
        mapPackageNameTotalAmount.clear();

        /******************/
        for(ProductWrapper w : oliMedicamentosOtrosList)
        {
            if(showChildProduct == false && w.oli.Child_Product__c)
                continue;
            if(w.oli.Original_Amount__c != null && w.oli.Quantity != null)
            {
                //This If condition will execute for the products which are part of the  packages.
                if(w.oli.Parent_Package_Code__c != null)
                {
                                  
                    if(w.product.Aplica_descuento__c)
                        
                        w.oli.TotalPrice = w.oli.Original_Amount__c * w.oli.Quantity * getApplicableDiscountPercentage(w.oli.Parent_Package_Code__c, w.product.Name);
                    	
                    else
                        w.oli.TotalPrice = w.oli.Original_Amount__c * w.oli.Quantity;
					
                    if(w.oli.TotalPrice != null)
                        w.oli.IVA__c = w.oli.TotalPrice  * (w.product.iva__c == null ? 0 :(w.product.iva__c/100));
                    
                    if(!mapPackageNameTotalAmount.containsKey(w.oli.Parent_Package_Code__c))
                    {
                        mapPackageNameTotalAmount.put(w.oli.Parent_Package_Code__c, w.oli.TotalPrice);
                    }
                    else
                    {
                        Decimal d1 = mapPackageNameTotalAmount.get(w.oli.Parent_Package_Code__c);
                        mapPackageNameTotalAmount.put(w.oli.Parent_Package_Code__c, w.oli.TotalPrice+d1);
                    }
                    
                    if(!mapPackageNameTotalIva.containsKey(w.oli.Parent_Package_Code__c))
                    {
                        mapPackageNameTotalIva.put(w.oli.Parent_Package_Code__c, w.oli.IVA__c);
                    }
                    else
                    {
                        Decimal d1 = mapPackageNameTotalIva.get(w.oli.Parent_Package_Code__c);
                        mapPackageNameTotalIva.put(w.oli.Parent_Package_Code__c, w.oli.IVA__c+d1);
                    }
                    
                }
                //This Else If condition will execute for the Individual products which are not part of the package.
                else if(w.oli.Parent_Package_Code__c == null && w.oli.Not_For_Create__c == false)
                {
                    decimal precioProducto = w.oli.Original_Amount__c + (ActivarMargen == true ? ( w.oli.Original_Amount__c * (((w.product.margenDeTipoDescuento__c==null ? 0 :w.product.margenDeTipoDescuento__c) )/100)):0);

                    if(w.product.Aplica_descuento__c)
                        w.oli.TotalPrice = precioProducto * w.oli.Quantity * getApplicableDiscountPercentage(w.oli.Main_Parent_Package_Code__c, w.product.Name);
                    else
                        w.oli.TotalPrice = precioProducto * w.oli.Quantity;
                    
                    if(w.oli.TotalPrice != null)
                    {
                        w.oli.IVA__c = w.oli.TotalPrice *  (w.product.iva__c == null ? 0 :(w.product.iva__c/100)) ;
                        medicamentosOtrosTotalSINDESCUENTO += (precioProducto * w.oli.Quantity);
                        medicamentosOtrosTotal += w.oli.TotalPrice;
                        medicamentosOtrosIVATotal += w.oli.IVA__c ;
                    }
                }
            }
        }
        System.Debug(' mapPackageNameTotalAmount test123455' + mapPackageNameTotalAmount);
        for(ProductWrapper w : oliMedicamentosOtrosList)
        {
             if(showChildProduct == false && w.oli.Child_Product__c)
                continue;
            if(w.oli.Original_Amount__c != null && w.oli.Quantity != null)
            {
                //All Packages
                if(w.oli.Parent_Package_Code__c == null && w.oli.Not_For_Create__c == true)
                {                    
                    System.Debug(' *****ONly Package  test123455' + w.oli);
                    //This condition will execute if there is no product in the package.
                    if(mapPackageNameTotalAmount.get(w.product.ProductCode) == null)
                    {
                        
                        decimal precioProducto = w.oli.Original_Amount__c + (ActivarMargen == true ? ( w.oli.Original_Amount__c * (((w.product.margenDeTipoDescuento__c==null ? 0 :w.product.margenDeTipoDescuento__c) )/100)):0);

                        w.oli.TotalPrice = precioProducto * w.oli.Quantity * getApplicableDiscountPercentage(w.oli.Parent_Package_Code__c, null);
                        w.oli.IVA__c = w.oli.TotalPrice *  (w.product.iva__c == null ? 0 :(w.product.iva__c/100));
                         medicamentosOtrosTotalSINDESCUENTO += (precioProducto * w.oli.Quantity);
                        medicamentosOtrosTotal += w.oli.TotalPrice;
                        medicamentosOtrosIVATotal += w.oli.IVA__c ;
                         
                    }
                    //This else Condition will execute for All the Packages if that contains product.
                    else
                    {
                        
                        decimal precioProducto = mapPackageNameTotalAmount.get(w.product.ProductCode) + (ActivarMargen == true ? ( mapPackageNameTotalAmount.get(w.product.ProductCode) * (((w.product.margenDeTipoDescuento__c==null ? 0 :w.product.margenDeTipoDescuento__c) )/100)):0);
                        w.oli.TotalPrice = precioProducto  * w.oli.Quantity;
                        
                        if(w.oli.TotalPrice != null)
                        {
                            w.oli.IVA__c = mapPackageNameTotalIva.get(w.product.ProductCode);
                            medicamentosOtrosTotal += w.oli.TotalPrice;
                             medicamentosOtrosTotalSINDESCUENTO += w.oli.TotalPrice;
                             
                            medicamentosOtrosIVATotal += w.oli.IVA__c ;
                            
                        }
                    }
                }
            }
        }
        mapPackageNameTotalAmount.clear();

        /******************/
        for(ProductWrapper w : oliHonorariosList)
        {
            if(showChildProduct == false && w.oli.Child_Product__c)
                continue;
            if(w.oli.Original_Amount__c != null && w.oli.Quantity != null)
            {
                //This If condition will execute for the products which are part of the  packages.
                if(w.oli.Parent_Package_Code__c != null)
                {
                                  
                    if(w.product.Aplica_descuento__c)
                        
                        w.oli.TotalPrice = w.oli.Original_Amount__c * w.oli.Quantity * getApplicableDiscountPercentage(w.oli.Parent_Package_Code__c, w.product.Name);
                    	
                    else
                        w.oli.TotalPrice = w.oli.Original_Amount__c * w.oli.Quantity;
					
                    if(w.oli.TotalPrice != null)
                        w.oli.IVA__c = w.oli.TotalPrice  * (w.product.iva__c == null ? 0 :(w.product.iva__c/100));
                    
                    if(!mapPackageNameTotalAmount.containsKey(w.oli.Parent_Package_Code__c))
                    {
                        mapPackageNameTotalAmount.put(w.oli.Parent_Package_Code__c, w.oli.TotalPrice);
                    }
                    else
                    {
                        Decimal d1 = mapPackageNameTotalAmount.get(w.oli.Parent_Package_Code__c);
                        mapPackageNameTotalAmount.put(w.oli.Parent_Package_Code__c, w.oli.TotalPrice+d1);
                    }
                    
                    if(!mapPackageNameTotalIva.containsKey(w.oli.Parent_Package_Code__c))
                    {
                        mapPackageNameTotalIva.put(w.oli.Parent_Package_Code__c, w.oli.IVA__c);
                    }
                    else
                    {
                        Decimal d1 = mapPackageNameTotalIva.get(w.oli.Parent_Package_Code__c);
                        mapPackageNameTotalIva.put(w.oli.Parent_Package_Code__c, w.oli.IVA__c+d1);
                    }
                    
                }
                //This Else If condition will execute for the Individual products which are not part of the package.
                else if(w.oli.Parent_Package_Code__c == null && w.oli.Not_For_Create__c == false)
                {
                    decimal precioProducto = w.oli.Original_Amount__c + (ActivarMargen == true ? ( w.oli.Original_Amount__c * (((w.product.margenDeTipoDescuento__c==null ? 0 :w.product.margenDeTipoDescuento__c) )/100)):0);

                    if(w.product.Aplica_descuento__c)
                        w.oli.TotalPrice = precioProducto * w.oli.Quantity * getApplicableDiscountPercentage(w.oli.Main_Parent_Package_Code__c, w.product.Name);
                    else
                        w.oli.TotalPrice = precioProducto * w.oli.Quantity;
                    
                    if(w.oli.TotalPrice != null)
                    {
                        w.oli.IVA__c = w.oli.TotalPrice *  (w.product.iva__c == null ? 0 :(w.product.iva__c/100)) ;
                        honorariosTotalSINDESCUENTO += (precioProducto * w.oli.Quantity);
                        honorariosTotal += w.oli.TotalPrice;
                        honorariosIVATotal += w.oli.IVA__c ;
                    }
                }
            }
        }
        System.Debug(' mapPackageNameTotalAmount test123455' + mapPackageNameTotalAmount);
        for(ProductWrapper w : oliHonorariosList)
        {
            if(showChildProduct == false && w.oli.Child_Product__c)
                continue;
            if(w.oli.Original_Amount__c != null && w.oli.Quantity != null)
            {
                //All Packages
                if(w.oli.Parent_Package_Code__c == null && w.oli.Not_For_Create__c == true)
                {                    
                    System.Debug(' *****ONly Package  test123455' + w.oli);
                    //This condition will execute if there is no product in the package.
                    if(mapPackageNameTotalAmount.get(w.product.ProductCode) == null)
                    {
                        
                         decimal precioProducto = w.oli.Original_Amount__c + (ActivarMargen == true ? ( w.oli.Original_Amount__c * (((w.product.margenDeTipoDescuento__c==null ? 0 :w.product.margenDeTipoDescuento__c) )/100)):0);

                        w.oli.TotalPrice = precioProducto * w.oli.Quantity * getApplicableDiscountPercentage(w.oli.Parent_Package_Code__c, null);
                        w.oli.IVA__c = w.oli.TotalPrice *  (w.product.iva__c == null ? 0 :(w.product.iva__c/100));
                         honorariosTotalSINDESCUENTO += (precioProducto * w.oli.Quantity);
                        honorariosTotal += w.oli.TotalPrice;
                        honorariosIVATotal += w.oli.IVA__c ;
                    }
                    //This else Condition will execute for All the Packages if that contains product.
                    else
                    {
                        
                        decimal precioProducto = mapPackageNameTotalAmount.get(w.product.ProductCode) + (ActivarMargen == true ? ( mapPackageNameTotalAmount.get(w.product.ProductCode) * (((w.product.margenDeTipoDescuento__c==null ? 0 :w.product.margenDeTipoDescuento__c) )/100)):0);
                        w.oli.TotalPrice = precioProducto  * w.oli.Quantity;
                        
                        if(w.oli.TotalPrice != null)
                        {
                            w.oli.IVA__c = mapPackageNameTotalIva.get(w.product.ProductCode);
                            honorariosTotal += w.oli.TotalPrice;
                             honorariosTotalSINDESCUENTO += w.oli.TotalPrice;
                             
                            honorariosIVATotal += w.oli.IVA__c ;
                        }
                    }
                }
            }
        }
        mapPackageNameTotalAmount.clear();
        
        /******************/
        for(ProductWrapper w : oliExternalList)
        {
            if(showChildProduct == false && w.oli.Child_Product__c)
                continue;
            if(w.oli.Original_Amount__c != null && w.oli.Quantity != null)
            {
                //This If condition will execute for the products which are part of the  packages.
                if(w.oli.Parent_Package_Code__c != null)
                {
                                  
                    if(w.product.Aplica_descuento__c)
                        
                        w.oli.TotalPrice = w.oli.Original_Amount__c * w.oli.Quantity * getApplicableDiscountPercentage(w.oli.Parent_Package_Code__c, w.product.Name);
                    	
                    else
                        w.oli.TotalPrice = w.oli.Original_Amount__c * w.oli.Quantity;
					
                    if(w.oli.TotalPrice != null)
                        w.oli.IVA__c = w.oli.TotalPrice  * (w.product.iva__c == null ? 0 :(w.product.iva__c/100));
                    
                    if(!mapPackageNameTotalAmount.containsKey(w.oli.Parent_Package_Code__c))
                    {
                        mapPackageNameTotalAmount.put(w.oli.Parent_Package_Code__c, w.oli.TotalPrice);
                    }
                    else
                    {
                        Decimal d1 = mapPackageNameTotalAmount.get(w.oli.Parent_Package_Code__c);
                        mapPackageNameTotalAmount.put(w.oli.Parent_Package_Code__c, w.oli.TotalPrice+d1);
                    }
                    
                    if(!mapPackageNameTotalIva.containsKey(w.oli.Parent_Package_Code__c))
                    {
                        mapPackageNameTotalIva.put(w.oli.Parent_Package_Code__c, w.oli.IVA__c);
                    }
                    else
                    {
                        Decimal d1 = mapPackageNameTotalIva.get(w.oli.Parent_Package_Code__c);
                        mapPackageNameTotalIva.put(w.oli.Parent_Package_Code__c, w.oli.IVA__c+d1);
                    }
                    
                }
                //This Else If condition will execute for the Individual products which are not part of the package.
                else if(w.oli.Parent_Package_Code__c == null && w.oli.Not_For_Create__c == false)
                {
                    decimal precioProducto = w.oli.Original_Amount__c + (ActivarMargen == true ? ( w.oli.Original_Amount__c * (((w.product.margenDeTipoDescuento__c==null ? 0 :w.product.margenDeTipoDescuento__c) )/100)):0);

                    if(w.product.Aplica_descuento__c)
                        w.oli.TotalPrice = precioProducto * w.oli.Quantity * getApplicableDiscountPercentage(w.oli.Main_Parent_Package_Code__c, w.product.Name);
                    else
                        w.oli.TotalPrice = precioProducto * w.oli.Quantity;
                    
                    if(w.oli.TotalPrice != null)
                    {
                        w.oli.IVA__c = w.oli.TotalPrice *  (w.product.iva__c == null ? 0 :(w.product.iva__c/100)) ;
                        externalTotalSINDESCUENTO += (precioProducto * w.oli.Quantity);
                        externalTotal += w.oli.TotalPrice;
                        externalIVATotal += w.oli.IVA__c ;
                        
                         
                    }
                }
            }
        }
        System.Debug(' mapPackageNameTotalAmount test123455' + mapPackageNameTotalAmount);
        for(ProductWrapper w : oliExternalList)
        {
            if(showChildProduct == false && w.oli.Child_Product__c)
                continue;
            if(w.oli.Original_Amount__c != null && w.oli.Quantity != null)
            {
                //All Packages
                if(w.oli.Parent_Package_Code__c == null && w.oli.Not_For_Create__c == true)
                {                    
                    System.Debug(' *****ONly Package  test123455' + w.oli);
                    //This condition will execute if there is no product in the package.
                    if(mapPackageNameTotalAmount.get(w.product.ProductCode) == null)
                    {
                        decimal precioProducto = w.oli.Original_Amount__c + (ActivarMargen == true ? ( w.oli.Original_Amount__c * (((w.product.margenDeTipoDescuento__c==null ? 0 :w.product.margenDeTipoDescuento__c) )/100)):0);

                        w.oli.TotalPrice = w.oli.Original_Amount__c * w.oli.Quantity * getApplicableDiscountPercentage(w.oli.Parent_Package_Code__c, null);
                        w.oli.IVA__c = w.oli.TotalPrice *  (w.product.iva__c == null ? 0 :(w.product.iva__c/100));
                         externalTotalSINDESCUENTO += (w.oli.Original_Amount__c * w.oli.Quantity);
                        externalTotal += w.oli.TotalPrice;
                        externalIVATotal += w.oli.IVA__c ;
                        
                         
                    }
                    //This else Condition will execute for All the Packages if that contains product.
                    else
                    {
                        
                        decimal precioProducto = mapPackageNameTotalAmount.get(w.product.ProductCode) + (ActivarMargen == true ? ( mapPackageNameTotalAmount.get(w.product.ProductCode) * (((w.product.margenDeTipoDescuento__c==null ? 0 :w.product.margenDeTipoDescuento__c) )/100)):0);
                        w.oli.TotalPrice = precioProducto  * w.oli.Quantity;
                        
                        if(w.oli.TotalPrice != null)
                        {
                            w.oli.IVA__c = mapPackageNameTotalIva.get(w.product.ProductCode);
                            externalTotal += w.oli.TotalPrice;
                            externalTotalSINDESCUENTO += w.oli.TotalPrice;
                            externalIVATotal += w.oli.IVA__c ;
                            
                          
                        }
                    }
                }
            }
        }
        mapPackageNameTotalAmount.clear();

       montoTotalOppConDescuento = serviceTotal + serviceOtrosTotal + insumosTotal + insumosOtrosTotal + medicamentosTotal  + medicamentosOtrosTotal + honorariosTotal + honorariosOtrosTotal + otrosTotal + externalTotal; 
        montoTotalOpp = serviceTotalSINDESCUENTO +  serviceOtrosTotalSINDESCUENTO +  insumosTotalSINDESCUENTO +  insumosOtrosTotalSINDESCUENTO +  medicamentosTotalSINDESCUENTO +  medicamentosOtrosTotalSINDESCUENTO +  honorariosTotalSINDESCUENTO +  honorariosOtrosTotalSINDESCUENTO +  otrosTotalSINDESCUENTO +  externalTotalSINDESCUENTO ;
       montoTotalIVAOpp =   serviceIVATotal + serviceOtrosIVATotal + insumosIVATotal + insumosOtrosIVATotal + medicamentosIVATotal + medicamentosOtrosIVATotal + honorariosIVATotal + honorariosOtrosIVATotal + otrosIVATotal + externalIVATotal;
       montoFinalOpp = montoTotalOppConDescuento + montoTotalIVAOpp;
    }
        @TestVisible
    private Decimal getApplicableDiscountPercentage(String packageName, String productName)
    {
      
        Lindora = 0;
        SanJose = 0;
        
        if(opportunity.Extra_Discount__c == null)
            opportunity.Extra_Discount__c = 0;
        
        Decimal applyDiscount = null;
        applyDiscount = mapProductNameAmount.get(productName);
        if(applyDiscount != null && discountName != 'Otros')
        {
            return calculateDiscount(applyDiscount);
        }
        
        applyDiscount = mapPackageNameAmount.get(packageName);
        if(applyDiscount != null && discountName != 'Otros')
        {
            return calculateDiscount(applyDiscount);
        }

        applyDiscount = mapSubPackagePackageAmount.get(packageName);
        if(applyDiscount != null && discountName != 'Otros')
        {
            return calculateDiscount(applyDiscount);
        }
        
        //We will get this from the picklist Value.
        applyDiscount = mapMainDiscountAmount.get(discountName);
        if(applyDiscount != null && discountName != 'Otros')
        {
            return calculateDiscount(applyDiscount);
        }
        if(discountName == 'Otros')
        {
            Decimal largeVal = 0;
            if(opportunity.Tipo_de_Oportunidad__c == 'Ambulatorio')
            {
                Decimal lindoraAmbulatorio = Decimal.ValueOf(System.Label.lindoraAmbulatorio);
                Decimal SanJoseAmbulatorio = Decimal.ValueOf(System.Label.SanJoseAmbulatorio);
                Lindora = lindoraAmbulatorio;
                SanJose = SanJoseAmbulatorio;
                largeVal= lindoraAmbulatorio > SanJoseAmbulatorio ? lindoraAmbulatorio : SanJoseAmbulatorio;
            }
            else if(opportunity.Tipo_de_Oportunidad__c == 'Hospitalización')
            {
                Decimal LindoraHospitalizacion = Decimal.ValueOf(System.Label.LindoraHospitalizacion);
                Decimal SanJoseHospitalizacion = Decimal.ValueOf(System.Label.SanJoseHospitalizacion);
                Lindora = LindoraHospitalizacion;
                SanJose = SanJoseHospitalizacion;
                largeVal= LindoraHospitalizacion > SanJoseHospitalizacion ? LindoraHospitalizacion : SanJoseHospitalizacion;
            }
            System.Debug(' Lindora ' + Lindora);
            System.Debug(' SanJose ' + SanJose);
            if(opportunity.Extra_Discount__c > 30)
            {
                if(opportunity.Approved_Discount__c)
                    applyDiscount =  (100 - opportunity.Extra_Discount__c - largeVal )/100;
                else
                    applyDiscount =  (100 - largeVal )/100;
            }
            else
            {
                applyDiscount = (100 - largeVal - opportunity.Extra_Discount__c)/100;
            }
            return applyDiscount;
        }
        if(opportunity.Extra_Discount__c != 0)
        {
            if(opportunity.Extra_Discount__c > 30)
            {
                if(opportunity.Approved_Discount__c){
                    
                }else{
                    applyDiscount =  (100 - opportunity.Extra_Discount__c)/100;
                }
                    
            }
            else
            {
                applyDiscount =  (100 - opportunity.Extra_Discount__c)/100;
            }
            return applyDiscount;
        }
        return 1;
    }
	@TestVisible
    private Decimal calculateDiscount(Decimal d)
    {
        if(opportunity.Extra_Discount__c > 30)
        {
            if(opportunity.Approved_Discount__c)
                d =  (100 - d - opportunity.Extra_Discount__c)/100;
            else
                d =  (100 - d)/100;
        }
        else
            d =  (100 - d - opportunity.Extra_Discount__c)/100;
        return d;
    }

    public void  changeDiscountDropDown(String discountName)
    {
    
        if(discountName != null && discountName != 'Otros'  && discountName != '')
        {
            System.Debug('discountName');
            //String discountRecordId = nivelDescuento.split('~~~~~')[0];
            List<nivelDescuento__c> lstDiscount = new List<nivelDescuento__c>();
            lstDiscount = [select Id, Name, Porcentaje_de_Descuento__c,(select Id, Paquete__r.Name,descuento__c, Paquete__r.Codigo__c from Descuentos_por_Paquetes__r), 
            (select id, Producto__r.Name, descuento__c from DescuentosPorProducto__r) from nivelDescuento__c where Name =:discountName];
            //where Id =:discountRecordId];
            for(nivelDescuento__c disc : lstDiscount)
            {
                mapMainDiscountAmount.put(disc.Name, disc.Porcentaje_de_Descuento__c);
                for(descuentosPorPaquetes__c discountPerPackage : (List<descuentosPorPaquetes__c>)disc.Descuentos_por_Paquetes__r)
                {
                    //paquete__c = Parent Package on paquetesPorPaquete__c object 
                    //Paquetes__c = Child Package on paquetesPorPaquete__c object
                    List<paquetesPorPaquete__c>  packagePerPackage = [select Id,paquete__r.Name, paquete__r.Codigo__c, Paquetes__r.Name, Paquetes__r.Codigo__c from paquetesPorPaquete__c where paquete__c = :discountPerPackage.Paquete__c];
                    for(paquetesPorPaquete__c ppp : packagePerPackage)
                    {
                        //mapSubPackagePackageAmount.put(ppp.Paquetes__r.name, ppp.paquete__r.name);
                        mapSubPackagePackageAmount.put(ppp.Paquetes__r.Codigo__c, discountPerPackage.descuento__c);
                    }                    
                    mapPackageNameAmount.put(discountPerPackage.Paquete__r.Codigo__c, discountPerPackage.descuento__c);
                }

                for(descuentosProducto__c discountPerProduct : (List<descuentosProducto__c>)disc.DescuentosPorProducto__r)
                {
                    mapProductNameAmount.put(discountPerProduct.Producto__r.Name, discountPerProduct.descuento__c);
                }
            }
        }    
        

    

    }
    
    
        

    public void addCostProduct()
    {

        
            stepNo = '3';
        Set<String> productIds = new Set<String>();
        List<listaDePrecios__c> paqueteList = [SELECT Paquete__r.Id, Paquete__r.Name, Paquete__r.montoKit__c, 
                                        Paquete__r.paquete__c, Paquete__r.Tipo__c, Paquete__r.Codigo__c                              
                                        FROM listaDePrecios__c 
                                        WHERE Paquete__r.Id =: paquete and listaDePrecios__c =:IdListaPrecios ];
        
        List<productoPorPaquete__c> prodPaqList = [SELECT Id, Name, Producto__c,Producto__r.margenDeTipoDescuento__c, cantidad__c, tipo__c, 
                                                   Producto__r.Name, Producto__r.ProductCode, Producto__r.Tipo__c,Producto__r.iva__c
                                                   FROM productoPorPaquete__c
                                                   WHERE Paquete__c =:paquete ];
        /*List<paquetesPorPaquete__c>  paquetesPorPaqueteList = [SELECT Id, Name, paquete__c, 
                                                                 Paquetes__c, tipo__c, Paquetes__r.Name
                                        FROM paquetesPorPaquete__c WHERE Paquete__c =: paqueteList[0].Paquete__r.Id];*/  
                                        
        
        /*List<paquete__c> paqueteList = [SELECT Id, montoKit__c, paquete__c, Tipo__c, 
                                       (SELECT Id, Name, Producto__c, cantidad__c, tipo__c, 
                                        Producto__r.Name, Producto__r.ProductCode, Producto__r.Tipo__c
                                        FROM productosPorPaquete__r),
                                       (SELECT Id, Name, paquete__c, Paquetes__c, tipo__c
                                        FROM Paquetes_por_Paquete1__r)
                                        FROM paquete__c 
                                        WHERE Id=: paquete];*/
        
        
        
        for(productoPorPaquete__c rec : prodPaqList)
        {
            productIds.add(rec.Producto__c);
        }
        
        List<PricebookEntry> productList = [select id,UnitPrice,  Product2.id,  Product2.Name,  Product2.ProductCode,Product2.iva__c,
                                               Product2.Tipo__c,Product2.margenDeTipoDescuento__c,  Product2.Aplica_descuento__c
                                      			from PricebookEntry
                                      			where Product2.id in : productIds and  PriceBook2Id =: IdListaPrecios];
        system.debug('Id lista: '+productIds);
        if(productList.size() > 0)
        {
            for(PricebookEntry p : productList)
            {
                OpportunityLineItem oli = new OpportunityLineItem(OpportunityId = opportunityId);
                
                
                    oli.PriceBookEntryId = p.id;
                    oli.Original_Amount__c = p.UnitPrice;
                
                
                if(p.Product2.Tipo__c == 'Servicios' && otros)
                {
                    oli.Otros__c = true;
                    oliServiceOtrosList.add(new ProductWrapper(oli, p.Product2));
                }
                else if(p.Product2.Tipo__c == 'Servicios')
                {
                    opportunity.Otros_Servicios__c = true;
                    oliServiceList.add(new ProductWrapper(oli, p.Product2));
                }
                else if(p.Product2.Tipo__c == 'Insumos' && otros)
                {
                    oli.Otros__c = true;
                    oliInsumosOtrosList.add(new ProductWrapper(oli, p.Product2));
                }
                else if(p.Product2.Tipo__c == 'Insumos')
                {
                    opportunity.Otros_Insumos__c = true;
                    oliInsumosList.add(new ProductWrapper(oli, p.Product2));
                }
                else if(p.Product2.Tipo__c == 'Medicamentos' && otros)
                {
                    oli.Otros__c = true;
                    oliMedicamentosOtrosList.add(new ProductWrapper(oli, p.Product2));
                }
                else if(p.Product2.Tipo__c == 'Medicamentos')
                {
                    opportunity.Otros_Medicamentos__c = true;
                    oliMedicamentosList.add(new ProductWrapper(oli, p.Product2));
                }
                
                else if(p.Product2.Tipo__c == 'External')
                {
                    oliExternalList.add(new ProductWrapper(oli, p.Product2));
                }
                else if(p.Product2.Tipo__c == 'Honorarios' && otros)
                {
                    oli.Otros__c = true;
                    oliHonorariosOtrosList.add(new ProductWrapper(oli, p.Product2));
                }
                else if(p.Product2.Tipo__c == 'Honorarios')
                {
                    opportunity.Otros_Honorarios__c = true;
                    oliHonorariosList.add(new ProductWrapper(oli, p.Product2));
                }
            }
        }

        List<OpportunityLineItem> olis = [select Id,  Product2.Name ,Product2.iva__c,iva__c, PriceBookEntryId from OpportunityLineItem where OpportunityId =:opportunityId and Product2.Description = 'DoctorFixedProduct'];
        if(olis.size() == 1 && contact != null && contact.Name != null && contact.Name != olis[0]. Product2.Name)
        {
            System.Debug(' If COndition ');
            //remove the old item from the list.
            deleteIndx = 0;
            oliHonorariosList = deleteUtil(oliHonorariosList);
            deleteIndx = null;

            //add new item in the list.
            if(language == 'Inglés')
            {
                System.Debug(' Inglés');
                List<Product2> pList = [select id, Name, ProductCode, Tipo__c, Aplica_descuento__c,margenDeTipoDescuento__c,iva__c, 
			(select id, UnitPrice from PriceBookEntries where PriceBook2Id =: IdListaPrecios )
                                        from Product2 where Name =:contact.Name];
                OpportunityLineItem oli = new OpportunityLineItem(OpportunityId = opportunityId);
                oli.Quantity = 1;
                oli.PriceBookEntryId = pList[0].PriceBookEntries[0].Id;////p.PriceBookEntries[0].Id;
                oli.TotalPrice =  pList[0].PriceBookEntries[0].UnitPrice;//p.PriceBookEntries[0].UnitPrice;
                oli.Original_Amount__c =  pList[0].PriceBookEntries[0].UnitPrice;//p.PriceBookEntries[0].UnitPrice;
                oli.Parent_Package_Code__c = null;
                oli.Part_of_Package__c = false;
                oliHonorariosList.add(new ProductWrapper(oli, pList[0]));
            }
            else if(language == 'Español')
            {
                System.Debug(' Español');
                List<Product2> pList = [select id, Name, ProductCode, Tipo__c, Aplica_descuento__c,margenDeTipoDescuento__c,iva__c, 
                                            (select id, UnitPrice from PriceBookEntries where PriceBook2Id =: IdListaPrecios )
                                            from Product2 
                                            where Name =:contact.Name];
                OpportunityLineItem oli = new OpportunityLineItem(OpportunityId = opportunityId);
                oli.Quantity = 1;
                oli.PriceBookEntryId =  pList[0].PriceBookEntries[0].Id;//p.PriceBookEntries[0].Id;
                oli.TotalPrice =  pList[0].PriceBookEntries[0].UnitPrice; //p.PriceBookEntries[0].UnitPrice;
                oli.Original_Amount__c =  pList[0].PriceBookEntries[0].UnitPrice;//p.PriceBookEntries[0].UnitPrice;
                oli.Parent_Package_Code__c = null;
                oli.Part_of_Package__c = false;
                oliHonorariosList.add(new ProductWrapper(oli, pList[0]));
            }
        }
        else if(contact != null && contact.Name != null && olis.size() == 0)
        {
            System.Debug(' Else If COndition ' + contact.Name);
            //Add the appropriate name from the list.
            if(language == 'Inglés')
            {
                System.Debug(' Inglés');
                List<Product2> pList = [select id, Name, ProductCode, Tipo__c, Aplica_descuento__c,margenDeTipoDescuento__c,iva__c,
                                         (select id, UnitPrice from PriceBookEntries where PriceBook2Id =: IdListaPrecios ) from Product2 where Name =:contact.Name];
                OpportunityLineItem oli = new OpportunityLineItem(OpportunityId = opportunityId);
                oli.Quantity = 1;
                oli.PriceBookEntryId = pList[0].PriceBookEntries[0].Id;////p.PriceBookEntries[0].Id;
                oli.TotalPrice =  pList[0].PriceBookEntries[0].UnitPrice;//p.PriceBookEntries[0].UnitPrice;
                oli.Original_Amount__c =  pList[0].PriceBookEntries[0].UnitPrice;//p.PriceBookEntries[0].UnitPrice;
                oli.Parent_Package_Code__c = null;
                oli.Part_of_Package__c = false;
                oliHonorariosList.add(new ProductWrapper(oli, pList[0]));
            }
            else if(language == 'Español')
            {
                System.Debug(' Español');
                List<Product2> pList = [select id, Name, ProductCode, Tipo__c, Aplica_descuento__c,margenDeTipoDescuento__c,iva__c,
                      (select id, UnitPrice from PriceBookEntries where PriceBook2Id =: IdListaPrecios ) from Product2 where Name =:contact.Name];
                OpportunityLineItem oli = new OpportunityLineItem(OpportunityId = opportunityId);
                oli.Quantity = 1;
                oli.PriceBookEntryId =  pList[0].PriceBookEntries[0].Id;//p.PriceBookEntries[0].Id;
                oli.TotalPrice =  pList[0].PriceBookEntries[0].UnitPrice; //p.PriceBookEntries[0].UnitPrice;
                oli.Original_Amount__c =  pList[0].PriceBookEntries[0].UnitPrice;//p.PriceBookEntries[0].UnitPrice;
                oli.Parent_Package_Code__c = null;
                oli.Part_of_Package__c = false;
                oliHonorariosList.add(new ProductWrapper(oli, pList[0]));
            }
        }
        calcTotal();
        
        
    }
    
    public void addManualProduct()
    {
        List<Product2> productList = [select id, Name, ProductCode,margenDeTipoDescuento__c, Tipo__c,iva__c, Aplica_descuento__c, 
                                     (select id, UnitPrice from PriceBookEntries
                                      where PriceBook2Id =: IdListaPrecios )
                                      from Product2 
                                      where Id =: productId];
        if(productList.size() == 0)
        {
            addProductLists(productId, true);
        }
        else
        {
            if(productList.size() > 0)
            {
                for(Product2 p : productList)
                {
                    OpportunityLineItem oli = new OpportunityLineItem(OpportunityId = opportunityId);
                    
                    if(p.PriceBookEntries.size() > 0)
                    {
                        oli.Quantity = 1;
                        oli.PriceBookEntryId = p.PriceBookEntries[0].Id;
                        oli.TotalPrice = p.PriceBookEntries[0].UnitPrice;
                        oli.Original_Amount__c = p.PriceBookEntries[0].UnitPrice;
                    }
                    oli.Parent_Package_Code__c = null;
                    if(p.ProductCode.startsWithIgnoreCase('SER'))
                    {
                        oli.Part_of_Package__c = false;
                        if(otros)
                        {
                            oli.Otros__c = true;
                            oliServiceOtrosList.add(new ProductWrapper(oli, p));
                        }
                        else
                            oliServiceList.add(new ProductWrapper(oli, p));
                    }
                    else if(p.ProductCode.startsWithIgnoreCase('INS') || p.ProductCode.startsWithIgnoreCase('MED'))
                    {
                        oli.Part_of_Package__c = false;
                        if(otros)
                        {
                            oli.Otros__c = true;
                            oliMedicamentosOtrosList.add(new ProductWrapper(oli, p));
                        }
                        else
                            oliMedicamentosList.add(new ProductWrapper(oli, p));
                    }
                    else if(p.ProductCode.startsWithIgnoreCase('MAT'))
                    {
                        oli.Part_of_Package__c = false;
                        if(otros)
                        {
                            oli.Otros__c = true;
                            oliInsumosOtrosList.add(new ProductWrapper(oli, p));
                        }
                        else
                            oliInsumosList.add(new ProductWrapper(oli, p));
                    }
                    else if(p.ProductCode.startsWithIgnoreCase('Hono'))
                    {
                        oli.Part_of_Package__c = false;
                        //if(otros)
                        //    oliHonorariosOtrosList.add(new ProductWrapper(oli, p));
                        //else
                            oliHonorariosList.add(new ProductWrapper(oli, p));
                    }
                    else if(p.ProductCode.startsWithIgnoreCase('EXT'))
                    {
                        oli.Part_of_Package__c = false;
                        oliExternalList.add(new ProductWrapper(oli, p));
                    }
                    else if(p.Tipo__c == 'Honorarios')
                    {
                        oli.Part_of_Package__c = false;
                        //if(otros)
                        //    oliHonorariosOtrosList.add(new ProductWrapper(oli, p));
                        //else
                            oliHonorariosList.add(new ProductWrapper(oli, p));
                    }
                    else if(p.Tipo__c == 'Medicamentos')
                    {
                        oli.Part_of_Package__c = false;
                        if(otros)
                        {
                            oli.Otros__c = true;
                            oliMedicamentosOtrosList.add(new ProductWrapper(oli, p));
                        }
                        else
                            oliMedicamentosList.add(new ProductWrapper(oli, p));
                    } 
                    else if(p.Tipo__c == 'Servicios')
                    {
                        oli.Part_of_Package__c = false;
                        if(otros)
                        {
                            oli.Otros__c = true;
                            oliServiceOtrosList.add(new ProductWrapper(oli, p));
                        }
                        else
                            oliServiceList.add(new ProductWrapper(oli, p));
                    } 
                    else if(p.Tipo__c == 'Insumos')
                    {
                        oli.Part_of_Package__c = false;
                        if(otros)
                        {
                            oli.Otros__c = true;
                            oliInsumosOtrosList.add(new ProductWrapper(oli, p));
                        }
                        else
                        {
                            oliInsumosList.add(new ProductWrapper(oli, p));
                        }
                    }   
                    else
                        oliOtrosList.add(new ProductWrapper(oli, p));
                }
                
                calcTotal();
            }
        }
    }
    
    public void showHideChildProduct()
    {
        if(opportunity.Vista__c == 'Cliente')
            showChildProduct = false;
        else
            showChildProduct = true;
            
        calcTotal();
    }
    
    boolean isService = false;
    boolean isInsumos = false;
    boolean isMedicamentos = false;
    boolean isHonorarios = false;
    public void addProductLists(String productId, boolean first)
    {  
        Map<String, Product2> productMap = new Map<String, Product2>();
        Map<String, Product2> productNameMap = new Map<String, Product2>();
            
        List<listaDePrecios__c> paqueteList = [SELECT Paquete__r.Id, Paquete__r.Name, Paquete__r.montoKit__c, 
                                        Paquete__r.paquete__c, Paquete__r.Tipo__c, Paquete__r.Codigo__c                              
                                        FROM listaDePrecios__c 
                                        WHERE Paquete__r.Id =: productId and listaDePrecios__c =:IdListaPrecios ];
        
        List<productoPorPaquete__c> productosPorPaqueteList = [SELECT Id, Name, Producto__c, cantidad__c,
                                                               tipo__c, precioProducto__c,Producto__r.margenDeTipoDescuento__c,
                                        Producto__r.Name,Producto__r.iva__c, Producto__r.ProductCode, Producto__r.Tipo__c
                                        FROM productoPorPaquete__c where Paquete__c =: paqueteList[0].Paquete__r.Id];
        
        List<paquetesPorPaquete__c>  paquetesPorPaqueteList = [SELECT Id, Name, paquete__c, 
                                                                 Paquetes__c, tipo__c, Paquetes__r.Name
                                        FROM paquetesPorPaquete__c WHERE Paquete__c =: paqueteList[0].Paquete__r.Id];   
                                        
        
       
                                        
        for(productoPorPaquete__c rec : [SELECT Id, Producto__c from productoPorPaquete__c WHERE Paquete__c =: productId])
        {
            productMap.put(rec.Producto__c, null);
        }
        
        String Pricebook2Id = [SELECT Pricebook2Id FROM Opportunity WHERE Id =: opportunityId].Pricebook2Id;
        
        List<Product2> productList = [select id,iva__c, Name, ProductCode,margenDeTipoDescuento__c, Tipo__c, Aplica_descuento__c, 
                                    (select id, UnitPrice from PriceBookEntries
                                      where PriceBook2Id =: IdListaPrecios )
                                      from Product2 
                                      where Id in: productMap.keyset()];
        
        System.debug('productList :::::' + productList.size());   
        
        for(Product2 rec : productList)
        {
            productMap.put(rec.Id, rec);
            productNameMap.put(rec.name, rec);
        }
        
        if(paqueteList.size() > 0)
        {
            List<listaDePrecios__c> dePreList = [SELECT Id, precioUnitario__c
                                                 FROM listaDePrecios__c
                                                 WHERE paquete__c =: productId
                                                 AND listaDePrecios__c =: pricebook2Id];
            
            if(!first || paquetesPorPaqueteList.size()==0)
            {
                OpportunityLineItem oli = new OpportunityLineItem(OpportunityId = opportunityId);
                oli.TotalPrice = (dePreList.size() > 0 ? dePreList[0].precioUnitario__c : paqueteList[0].Paquete__r.montoKit__c);
                oli.Original_Amount__c = (dePreList.size() > 0 ? dePreList[0].precioUnitario__c : paqueteList[0].Paquete__r.montoKit__c);
                
                oli.Quantity = 1;
                oli.Not_For_Create__c = true;
                oli.Parent_Package_Code__c = null;
                if(productNameMap.get(paqueteList[0].Paquete__r.Name) != null )
                {
                    Product2 p = new Product2(Name = paqueteList[0].Name, 
                                              ProductCode = productNameMap.get(paqueteList[0].Paquete__r.Name).ProductCode);
                    Rename_Package__c renamePack = new Rename_Package__c();
                    renamePack.Name = p.Name;
                    renamePack.New_Name__c = p.Name;
                    renamePack.Opportunity__c =opportunity.Id;
                    insert  renamePack;
                    mapRenamePackageName.put(renamePack.Name, renamePack.New_Name__c);
                    if(paqueteList[0].Paquete__r.Tipo__c == 'Servicios' && otros)
                    {
                        oli.Otros__c = true;
                        isService = true;
                        oliServiceOtrosList.add(new ProductWrapper(oli, p));
                    }
                    else if(paqueteList[0].Paquete__r.Tipo__c == 'Servicios')
                    {
                        isService = true;
                        oliServiceList.add(new ProductWrapper(oli, p));
                    }
                    else if(paqueteList[0].Paquete__r.Tipo__c == 'Insumos' && otros)
                    {
                        oli.Otros__c = true;
                        isInsumos = true;
                        oliInsumosOtrosList.add(new ProductWrapper(oli, p));
                    }
                    else if(paqueteList[0].Paquete__r.Tipo__c == 'Insumos')
                    {
                        isInsumos = true;
                        oliInsumosList.add(new ProductWrapper(oli, p));
                    }
                    else if(paqueteList[0].Paquete__r.Tipo__c == 'Medicamentos' && otros)
                    {
                        oli.Otros__c = true;
                        isMedicamentos = true;
                        oliMedicamentosOtrosList.add(new ProductWrapper(oli, p));
                    }
                    else if(paqueteList[0].Paquete__r.Tipo__c == 'Medicamentos')
                    {
                        isMedicamentos = true;
                        oliMedicamentosList.add(new ProductWrapper(oli, p));
                    }
                    else if(paqueteList[0].Paquete__r.Tipo__c == 'Honorarios' && otros)
                    {
                        oli.Otros__c = true;
                        isHonorarios = true;
                        oliHonorariosOtrosList.add(new ProductWrapper(oli, p));
                    }
                    else if(paqueteList[0].Paquete__r.Tipo__c == 'Honorarios')
                    {
                        isHonorarios = true;
                        oliHonorariosList.add(new ProductWrapper(oli, p));
                    }
                }
                else
                {
                    Product2 p = new Product2(Name = paqueteList[0].Paquete__r.Name, ProductCode = paqueteList[0].Paquete__r.Codigo__c);
                    Rename_Package__c renamePack = new Rename_Package__c();
                    renamePack.Name = p.Name;
                    renamePack.New_Name__c = p.Name;
                    renamePack.Opportunity__c =opportunity.Id;
                    insert  renamePack;
                    mapRenamePackageName.put(renamePack.Name, renamePack.New_Name__c);
                    if(paqueteList[0].Paquete__r.Tipo__c == 'Servicios' && otros)
                    {
                        oli.Otros__c = true;
                        isService = true;
                        oliServiceOtrosList.add(new ProductWrapper(oli, p));
                    }
                    else if(paqueteList[0].Paquete__r.Tipo__c == 'Servicios')
                    {
                        isService = true;
                        oliServiceList.add(new ProductWrapper(oli, p));
                    }
                    else if(paqueteList[0].Paquete__r.Tipo__c == 'Insumos' && otros)
                    {
                        oli.Otros__c = true;
                        isInsumos = true;
                        oliInsumosOtrosList.add(new ProductWrapper(oli, p));
                    }
                    else if(paqueteList[0].Paquete__r.Tipo__c == 'Insumos')
                    {
                        isInsumos = true;
                        oliInsumosList.add(new ProductWrapper(oli, p));
                    }
                    else if(paqueteList[0].Paquete__r.Tipo__c == 'Medicamentos' && otros)
                    {
                        oli.Otros__c = true;
                        isMedicamentos = true;
                        oliMedicamentosOtrosList.add(new ProductWrapper(oli, p));
                    }
                    else if(paqueteList[0].Paquete__r.Tipo__c == 'Medicamentos')
                    {
                        isMedicamentos = true;
                        oliMedicamentosList.add(new ProductWrapper(oli, p));
                    }
                    else if(paqueteList[0].Paquete__r.Tipo__c == 'Honorarios' && otros)
                    {
                        oli.Otros__c = true;
                        isHonorarios = true;
                        oliHonorariosOtrosList.add(new ProductWrapper(oli, p));
                    }
                    else if(paqueteList[0].Paquete__r.Tipo__c == 'Honorarios')
                    {
                        isHonorarios = true;
                        oliHonorariosList.add(new ProductWrapper(oli, p));
                    }
                }
            }
            
            System.debug('paqueteList[0].productosPorPaquete__r :::::' + productosPorPaqueteList.size());
            for(productoPorPaquete__c p : productosPorPaqueteList)
            {
                OpportunityLineItem oli = new OpportunityLineItem(OpportunityId = opportunityId);
                if(first == true)
                {
                    oli.Main_Parent_Package_Code__c = paqueteList[0].Paquete__r.Codigo__c;
                    oli.Parent_Package_Code__c = null;
                }
                else
                {
                    oli.Parent_Package_Code__c = paqueteList[0].Paquete__r.Codigo__c;
                }
                if(productMap.get(p.Producto__c).PriceBookEntries.size() > 0)
                {
                    oli.PriceBookEntryId = productMap.get(p.Producto__c).PriceBookEntries[0].Id;
                    oli.TotalPrice = productMap.get(p.Producto__c).PriceBookEntries[0].UnitPrice * p.cantidad__c;
                    oli.Original_Amount__c = productMap.get(p.Producto__c).PriceBookEntries[0].UnitPrice;
                }
                
                //oli.TotalPrice = p.precioProducto__c;
                oli.Quantity = p.cantidad__c;
                System.debug('paqueteList[0].Tipo__c :::::' + paqueteList[0].Paquete__r.Tipo__c);
                if(first)
                {
                    if(paquetesPorPaqueteList.size()==0)
                        oli.Child_Product__c = true;
                    
                    if(paqueteList[0].Paquete__r.Name.startsWithIgnoreCase('Kits'))
                    {
                        isInsumos = true;
                        if(otros)
                        {
                            oli.Otros__c = true;
                            oliInsumosOtrosList.add(new ProductWrapper(oli, productMap.get(p.Producto__c)));
                        }
                        else
                            oliInsumosList.add(new ProductWrapper(oli, productMap.get(p.Producto__c)));
                    }
                    else if(paqueteList[0].Paquete__r.Name.startsWithIgnoreCase('INS'))
                    {
                        isInsumos = true;
                        if(otros)
                        {
                            oli.Otros__c = true;
                            oliMedicamentosOtrosList.add(new ProductWrapper(oli, productMap.get(p.Producto__c)));
                        }
                        else
                            oliMedicamentosList.add(new ProductWrapper(oli, productMap.get(p.Producto__c)));
                    }
                    else if(p.Tipo__c == 'Servicios' && otros)
                    {
                        oli.Otros__c = true;
                        isService = true;
                        oliServiceOtrosList.add(new ProductWrapper(oli, productMap.get(p.Producto__c)));
                    }
                    else if(p.Tipo__c == 'Servicios')
                    {
                        isService = true;
                        oliServiceList.add(new ProductWrapper(oli, productMap.get(p.Producto__c)));
                    }
                    else if(p.Tipo__c == 'Insumos' && otros)
                    {
                        oli.Otros__c = true;
                        isInsumos = true;
                        oliInsumosOtrosList.add(new ProductWrapper(oli, productMap.get(p.Producto__c)));
                    }
                    else if(p.Tipo__c == 'Insumos')
                    {
                        isInsumos = true;
                        oliInsumosList.add(new ProductWrapper(oli, productMap.get(p.Producto__c)));
                    }
                    else if(p.Tipo__c == 'Medicamentos' && otros)
                    {
                        oli.Otros__c = true;
                        isMedicamentos = true;
                        oliMedicamentosOtrosList.add(new ProductWrapper(oli, productMap.get(p.Producto__c)));
                    }
                   	else if(p.Tipo__c == 'Medicamentos')
                    {
                        isMedicamentos = true;
                        oliMedicamentosList.add(new ProductWrapper(oli, productMap.get(p.Producto__c)));
                    }
                    else if(p.Tipo__c == 'Honorarios' && otros)
                    {
                        oli.Otros__c = true;
                        isHonorarios = true;
                        oliHonorariosOtrosList.add(new ProductWrapper(oli, productMap.get(p.Producto__c)));
                    }
                    else if(p.Tipo__c == 'Honorarios')
                    {
                        isHonorarios = true;
                        oliHonorariosList.add(new ProductWrapper(oli, productMap.get(p.Producto__c)));
                    }
                }
                else
                {
                    oli.Child_Product__c = true;
                    if(paqueteList[0].Paquete__r.Name.startsWithIgnoreCase('Kits'))
                    {
                        isInsumos = true;
                        if(otros)
                        {
                            oli.Otros__c = true;
                            oliInsumosOtrosList.add(new ProductWrapper(oli, productMap.get(p.Producto__c)));
                        }
                        else
                            oliInsumosList.add(new ProductWrapper(oli, productMap.get(p.Producto__c)));
                    }
                    else if(paqueteList[0].Paquete__r.Name.startsWithIgnoreCase('INS'))
                    {
                        isInsumos = true;
                        if(otros)
                        {
                            oli.Otros__c = true;
                            oliMedicamentosOtrosList.add(new ProductWrapper(oli, productMap.get(p.Producto__c)));
                        }
                        else
                            oliMedicamentosList.add(new ProductWrapper(oli, productMap.get(p.Producto__c)));
                    }
                    else if(paqueteList[0].Paquete__r.Tipo__c == 'Servicios' && otros)
                    {
                        oli.Otros__c = true;
                        isService = true;
                        oliServiceOtrosList.add(new ProductWrapper(oli, productMap.get(p.Producto__c)));
                    }
                    else if(paqueteList[0].Paquete__r.Tipo__c == 'Servicios')
                    {
                        isService = true;
                        oliServiceList.add(new ProductWrapper(oli, productMap.get(p.Producto__c)));
                    }
                    else if(paqueteList[0].Paquete__r.Tipo__c == 'Insumos' && otros)
                    {
                        isInsumos = true;
                        oli.Otros__c = true;
                        oliInsumosOtrosList.add(new ProductWrapper(oli, productMap.get(p.Producto__c)));
                    }
                    else if(paqueteList[0].Paquete__r.Tipo__c == 'Insumos')
                    {
                        isInsumos = true;
                        oliInsumosList.add(new ProductWrapper(oli, productMap.get(p.Producto__c)));
                    }
                    else if(paqueteList[0].Paquete__r.Tipo__c == 'Medicamentos' && otros)
                    {
                        oli.Otros__c = true;
                        isMedicamentos = true;
                        oliMedicamentosOtrosList.add(new ProductWrapper(oli, productMap.get(p.Producto__c)));
                    }
                    else if(paqueteList[0].Paquete__r.Tipo__c == 'Medicamentos')
                    {
                        isMedicamentos = true;
                        oliMedicamentosList.add(new ProductWrapper(oli, productMap.get(p.Producto__c)));
                    }
                    else if(paqueteList[0].Paquete__r.Tipo__c == 'Honorarios' && otros)
                    {
                        oli.Otros__c = true;
                        isHonorarios = true;
                        oliHonorariosOtrosList.add(new ProductWrapper(oli, productMap.get(p.Producto__c)));
                    }
                    else if(paqueteList[0].Paquete__r.Tipo__c == 'Honorarios')
                    {
                        isHonorarios = true;
                        oliHonorariosList.add(new ProductWrapper(oli, productMap.get(p.Producto__c)));
                    }
                }
            }
            
            System.debug('paqueteList[0].Paquetes_por_Paquete__r :::::' + paquetesPorPaqueteList.size());
            for(paquetesPorPaquete__c p : paquetesPorPaqueteList)
            {
                addProductLists(p.Paquetes__c, false);
            }
        }
        calcTotal();
    }
    
    
    @RemoteAction
    public static List<PackageWrapper> searchProducts(String productStr, String tipo,String idOpp)
    {
        
        System.debug('Entro: '+idOpp);
        Opportunity opp = [Select ID,Name,Pricebook2Id FROM Opportunity Where Id =: idOpp];
        system.debug('Lista de precios: '+opp.Pricebook2Id);
        productStr = '%' + productStr + '%';
        List<PricebookEntry> productList = new List<PricebookEntry>();
        List<PackageWrapper> packageList = new List<PackageWrapper>();
        
        if(tipo == 'All')
        {
            productList = [select Product2Id, Product2.Name, Product2.Description
                           from PricebookEntry 
                           where (Product2.Name like : productStr
                           or Product2.ProductCode like : productStr) AND Pricebook2Id =: opp.Pricebook2Id
                           order by Product2.Name];
             system.debug('productList: '+productList.size());
        }
        else
        {
            productList = [select Product2Id, Product2.Name, Product2.Description
                           from PricebookEntry 
                           where (Product2.Name like : productStr
                           or Product2.ProductCode like : productStr)
                           and Product2.Tipo__c  =: tipo AND Pricebook2Id =: opp.Pricebook2Id
                           order by Product2.Name];
            system.debug('productList: '+productList.size());
        }
        
        for(PricebookEntry rec : productList)
        {
            packageList.add(new PackageWrapper(rec.Product2Id, rec.Product2.Name));
        }
        
        for(listaDePrecios__c rec : [SELECT paquete__r.Id, paquete__r.Name from  listaDePrecios__c  where paquete__r.Name like : productStr AND listaDePrecios__c =: opp.Pricebook2Id ])
        {
            packageList.add(new PackageWrapper(rec.paquete__r.Id, rec.paquete__r.Name));
        }
            
        return packageList;
    }
    
    public class PackageWrapper
    {
        public String id;
        public String name;
        
        public PackageWrapper(String id, String name)
        {
            this.id = id;
            this.name = name;
        }
    }
    
    @RemoteAction
    public static List<Contact> searchContacts(String contactStr)
    {
        contactStr = contactStr + '%';
        List<Contact> contactList = new List<Contact>();
        contactList = [select id, Name, Description 
                       from Contact
                       where Name like : contactStr
                       and RecordType.Name = 'Médico'
                       order by Name];
        
        return contactList;
    }
    
    public void goToStepPDF()
    {
        if(bloquearProceso == 'false'){
        stepNo = '4';
        calcTotal();
        }
    }
    
    public PageReference saveProducts()
    {
        List<OpportunityLineItem> oliList = new List<OpportunityLineItem>();
        List<Opportunity_Package__c> oliPackageList = new List<Opportunity_Package__c>();
        Integer j=0;
        for(ProductWrapper w : oliServiceList)
        {
            if(!w.oli.Not_For_Create__c) 
            {
                w.oli.Category__c = 'SERVICIOS HOSPITALARIOS';
                w.oli.Location_Index_Product__c = j;
                w.oli.Descripcion_Custom__c = w.product.Name;
                j++;
                oliList.add(w.oli);
            }
            else
            {
                Opportunity_Package__c op = new Opportunity_Package__c();
                op.IVA__c = w.oli.IVA__c;
                op.Not_For_Create__c = false;
                op.Descripcion_Custom__c = w.product.Name;
                op.Opportunity__c = w.oli.OpportunityId;
                op.Name = w.product.Name;
                op.Original_Amount__c = w.oli.Original_Amount__c;
                op.Quantity__c = w.oli.Quantity;
                op.Total_Price__c = w.oli.TotalPrice;                        
                op.Category__c = 'SERVICIOS HOSPITALARIOS';
                op.ProductCode__c = w.product.ProductCode;
                op.Location_Index_Package__c = j;
                j++;
                oliPackageList.add(op);
            }
        }
        
        for(ProductWrapper w : oliServiceOtrosList)
        {
            if(!w.oli.Not_For_Create__c)
            {
                w.oli.Category__c = 'OTROS SERVICIOS';
                w.oli.Location_Index_Product__c = j;
                w.oli.Descripcion_Custom__c = w.product.Name;
                j++;
                oliList.add(w.oli);
            }
            else
            {
                Opportunity_Package__c op = new Opportunity_Package__c();
                op.IVA__c = w.oli.IVA__c;
                op.Not_For_Create__c = false;
                op.Opportunity__c = w.oli.OpportunityId;
                op.Name = w.product.Name;
                op.Descripcion_Custom__c = w.product.Name;
                op.Original_Amount__c = w.oli.Original_Amount__c;
                op.Quantity__c = w.oli.Quantity;
                op.Total_Price__c = w.oli.TotalPrice;                        
                op.Category__c = 'OTROS SERVICIOS';
                op.ProductCode__c = w.product.ProductCode;
                op.Otros__c = true;
                op.Location_Index_Package__c = j;
                j++;
                oliPackageList.add(op);
            } 
        }
        
        for(ProductWrapper w : oliInsumosList)
        {
            if(!w.oli.Not_For_Create__c)
            {
                w.oli.Category__c = 'INSUMOS HOSPITALARIOS';
                w.oli.Location_Index_Product__c = j;
                w.oli.Descripcion_Custom__c = w.product.Name;
                j++;
                oliList.add(w.oli);
            }
            else
            {
                Opportunity_Package__c op = new Opportunity_Package__c();
                op.IVA__c = w.oli.IVA__c;
                op.Not_For_Create__c = false;
                op.Descripcion_Custom__c = w.product.Name;
                op.Opportunity__c = w.oli.OpportunityId;
                op.Name = w.product.Name;
                op.Original_Amount__c = w.oli.Original_Amount__c;
                op.Quantity__c = w.oli.Quantity;
                op.Total_Price__c = w.oli.TotalPrice;                        
                op.Category__c = 'INSUMOS HOSPITALARIOS';
                op.ProductCode__c = w.product.ProductCode;
                op.Location_Index_Package__c = j;
                j++;
                oliPackageList.add(op);
            }
        }
        
        for(ProductWrapper w : oliInsumosOtrosList)
        {
            if(!w.oli.Not_For_Create__c)
            {
                w.oli.Category__c = 'OTROS INSUMOS';
                w.oli.Location_Index_Product__c = j;
                w.oli.Descripcion_Custom__c = w.product.Name;
                j++;
                oliList.add(w.oli);
            }
            else
            {
                Opportunity_Package__c op = new Opportunity_Package__c();
                op.IVA__c = w.oli.IVA__c;
                op.Not_For_Create__c = false;
                op.Descripcion_Custom__c = w.product.Name;
                op.Opportunity__c = w.oli.OpportunityId;
                op.Name = w.product.Name;
                op.Original_Amount__c = w.oli.Original_Amount__c;
                op.Quantity__c = w.oli.Quantity;
                op.Total_Price__c = w.oli.TotalPrice;                        
                op.Category__c = 'OTROS INSUMOS';
                op.ProductCode__c = w.product.ProductCode;
                op.Otros__c = true;
                op.Location_Index_Package__c = j;
                j++;
                oliPackageList.add(op);
            } 
        }
        
        for(ProductWrapper w : oliMedicamentosList)
        {
            if(!w.oli.Not_For_Create__c) 
            {
                w.oli.Category__c = 'MEDICAMENTOS HOSPITALARIOS';
                w.oli.Location_Index_Product__c = j;
                w.oli.Descripcion_Custom__c = w.product.Name;
                j++;
                oliList.add(w.oli);
            }
            else
            {
                Opportunity_Package__c op = new Opportunity_Package__c();
                op.IVA__c = w.oli.IVA__c;
                op.Not_For_Create__c = false;
                op.Opportunity__c = w.oli.OpportunityId;
                op.Name = w.product.Name;
                op.Descripcion_Custom__c = w.product.Name;
                op.Original_Amount__c = w.oli.Original_Amount__c;
                op.Quantity__c = w.oli.Quantity;
                op.Total_Price__c = w.oli.TotalPrice;                        
                op.Category__c = 'MEDICAMENTOS HOSPITALARIOS';
                op.ProductCode__c = w.product.ProductCode;
                op.Location_Index_Package__c = j;
                j++;
                oliPackageList.add(op);
            }
        }

        for(ProductWrapper w : oliHonorariosList)
        {
            if(!w.oli.Not_For_Create__c) 
            {
                w.oli.Category__c = 'HONORARIOS HOSPITALARIOS';
                w.oli.Location_Index_Product__c = j;
                w.oli.Descripcion_Custom__c = w.product.Name;
                j++;
                oliList.add(w.oli);
            }
            else
            {
               
                Opportunity_Package__c op = new Opportunity_Package__c();
                op.IVA__c = w.oli.IVA__c;
                op.Descripcion_Custom__c =w.product.Name;
                op.Not_For_Create__c = false;
                op.Opportunity__c = w.oli.OpportunityId;
                op.Name = w.product.Name;
                op.Original_Amount__c = w.oli.Original_Amount__c;
                op.Quantity__c = w.oli.Quantity;
                op.Total_Price__c = w.oli.TotalPrice;                        
                op.Category__c = 'HONORARIOS HOSPITALARIOS';
                op.ProductCode__c = w.product.ProductCode;
                op.Location_Index_Package__c = j;
                j++;
                oliPackageList.add(op);
            } 
        }
        
        for(ProductWrapper w : oliMedicamentosOtrosList)
        {
            if(!w.oli.Not_For_Create__c)
            {
                w.oli.Category__c = 'OTROS MEDICAMENTOS';
                w.oli.Location_Index_Product__c = j;
                w.oli.Descripcion_Custom__c = w.product.Name;
                j++;
                oliList.add(w.oli);
            }
            else
            {
                Opportunity_Package__c op = new Opportunity_Package__c();
                op.IVA__c = w.oli.IVA__c;
                op.Not_For_Create__c = false;
                op.Opportunity__c = w.oli.OpportunityId;
                op.Name = w.product.Name;
                op.Descripcion_Custom__c = w.product.Name;
                op.Original_Amount__c = w.oli.Original_Amount__c;
                op.Quantity__c = w.oli.Quantity;
                op.Total_Price__c = w.oli.TotalPrice;                        
                op.Category__c = 'OTROS MEDICAMENTOS';
                op.Otros__c = true;
                op.ProductCode__c = w.product.ProductCode;
                op.Location_Index_Package__c = j;
                j++;
                oliPackageList.add(op);
            } 
        }
        
        for(ProductWrapper w : oliExternalList)
        {
            if(!w.oli.Not_For_Create__c) 
            {
                w.oli.Category__c = 'EXTERNAL HOSPITALARIOS';
                w.oli.Location_Index_Product__c = j;
                w.oli.Descripcion_Custom__c = w.product.Name;
                j++;
                oliList.add(w.oli);
            }
            else
            {
                Opportunity_Package__c op = new Opportunity_Package__c();
                op.IVA__c = w.oli.IVA__c;
                op.Not_For_Create__c = false;
                op.Opportunity__c = w.oli.OpportunityId;
                op.Name = w.product.Name;
                op.Descripcion_Custom__c = w.product.Name;
                op.Original_Amount__c = w.oli.Original_Amount__c;
                op.Quantity__c = w.oli.Quantity;
                op.Total_Price__c = w.oli.TotalPrice;                        
                op.Category__c = 'EXTERNAL HOSPITALARIOS';
                op.ProductCode__c = w.product.ProductCode;
                op.Location_Index_Package__c = j;
                j++;
                oliPackageList.add(op);
            }
        }

        for(ProductWrapper w : oliHonorariosOtrosList)
        {
            if(!w.oli.Not_For_Create__c)
            {
                w.oli.Category__c = 'OTROS HONORARIOS';
                w.oli.Location_Index_Product__c = j;
                w.oli.Descripcion_Custom__c = w.product.Name;
                j++;
                oliList.add(w.oli);
            }
            else
            {
                Opportunity_Package__c op = new Opportunity_Package__c();
                op.IVA__c = w.oli.IVA__c;
                op.Not_For_Create__c = false;
                op.Opportunity__c = w.oli.OpportunityId;
                op.Name = w.product.Name;
                op.Descripcion_Custom__c =' w.product.Name';
                op.Original_Amount__c = w.oli.Original_Amount__c;
                op.Quantity__c = w.oli.Quantity;
                op.Total_Price__c = w.oli.TotalPrice;                        
                op.Category__c = 'OTROS HONORARIOS';
                op.Otros__c = true;
                op.ProductCode__c = w.product.ProductCode;
                op.Location_Index_Package__c = j;
                j++;
                oliPackageList.add(op);
            } 
        }

        for(ProductWrapper w : oliOtrosList)
        {
            if(!w.oli.Not_For_Create__c)
            {
                w.oli.Category__c = 'OTROS';
                w.oli.Location_Index_Product__c = j;
                j++;
                oliList.add(w.oli);
            }
            else
            {
                Opportunity_Package__c op = new Opportunity_Package__c();
                op.IVA__c = w.oli.IVA__c;
                op.Not_For_Create__c = false;
                op.Opportunity__c = w.oli.OpportunityId;
                op.Name = w.product.Name;
                op.Descripcion_Custom__c = w.product.Name;
                op.Original_Amount__c = w.oli.Original_Amount__c;
                op.Quantity__c = w.oli.Quantity;
                op.Total_Price__c = w.oli.TotalPrice;                        
                op.Category__c = 'OTROS';
                op.ProductCode__c = w.product.ProductCode;
                op.Location_Index_Package__c = j;
                j++;
                oliPackageList.add(op);
            }
        }
        
        try
        {
            upsert oliList;
            update account;
            opportunity.Impuesto_al_Valor_Agregado_IVA__c = montoTotalIVAOpp;
            opportunity.PrecioAproximado_de_la_Cirugia__c = montoTotalOpp;
            opportunity.Precio_aproximado_de_la_Cirugia_con_Desc__c	 = montoTotalOppConDescuento;
            opportunity.Precio_aproximado_de_la_Cirugia_IVA__c = montoFinalOpp;
            if(nivelDescuento != null && nivelDescuento.contains('~~~~~'))
                opportunity.Nivel_de_descuento__c = nivelDescuento.split('~~~~~')[0];
            
            update opportunity;
            
            if(oliDeleteList.size() > 0)
                delete oliDeleteList;
            
            delete [select id from Opportunity_Package__c where Opportunity__c =: opportunity.Id];
            insert oliPackageList;
            return new PageReference('/'+opportunityId).setRedirect(true);
        }
        catch(Exception ex)
        {   
            ApexPages.addMessage(new ApexPages.message(Apexpages.Severity.ERROR, ex.getMessage()));
            
            return null;
        }
    }
    
    public void saveProductsPDF()
    {
        saveProducts();
    }
    
    public PageReference rediretToOpportunity()
    {
        return new PageReference('/'+opportunityId).setRedirect(true);
    }
    
    public void renamePackageSave()
    {
        System.debug('renamePackageSave invoked ::');
         try
        {  
            if(!String.isEmpty(oldValue) && !String.isEmpty(newValue))
            {
               
                Map<STring, Rename_Package__c> mappackageOldNamePackageNewName = new Map<STring, Rename_Package__c>();
                for(Rename_Package__c renamePackageRec : [ select name, New_Name__c from Rename_Package__c where Opportunity__c =:opportunity.Id])
                {
                    mappackageOldNamePackageNewName.put(renamePackageRec.Name.trim(),renamePackageRec);
                }

                Rename_Package__c pk = new Rename_Package__c();
                System.Debug(' oldValue '+ oldValue);
                pk = mappackageOldNamePackageNewName.get(oldValue.trim());
                System.Debug(' pk '+ pk);
                if(pk != null)
                {
                    System.Debug(' newValue '+ newValue);
                    pk.New_Name__c = newValue;
                    update  pk;
                    mapRenamePackageName.put(pk.Name.trim(), pk.New_Name__c);
                }
            }            
        }
        catch(Exception ex)
        {
            System.assert(false, ex);
            ApexPages.addMessage(new ApexPages.message(Apexpages.Severity.ERROR, ex.getMessage()));
        }
    }

    public String getSpanishCreatedDate()
    {
        DateTime dt = opportunity.CreatedDate;
        String dateT = dt.format('dd MMMM YYYY');
        List<String> dateSplit = dateT.split(' ');
        if(dateSplit[1] == 'January')
        {
            return dateSplit[0] + ' enero ' + dateSplit[2];
        }
        else if(dateSplit[1] == 'February')
        {
            return dateSplit[0] + ' febrero ' + dateSplit[2];
        }
        else if(dateSplit[1] == 'March')
        {
            return dateSplit[0] + ' de marzo de ' + dateSplit[2];
        }
        else if(dateSplit[1] == 'April')
        {
            return dateSplit[0] + ' de abril de ' + dateSplit[2];
        }
        else if(dateSplit[1] == 'May')
        {
            return dateSplit[0] + ' mayo ' + dateSplit[2];
        }
        else if(dateSplit[1] == 'June')
        {
            return dateSplit[0] + ' junio ' + dateSplit[2];
        }
        else if(dateSplit[1] == 'July')
        {
            return dateSplit[0] + ' julio ' + dateSplit[2];
        }
        else if(dateSplit[1] == 'August')
        {
            return dateSplit[0] + ' de agosto de ' + dateSplit[2];
        }
        else if(dateSplit[1] == 'September')
        {
            return dateSplit[0] + ' de septiembre de ' + dateSplit[2];
        }
        else if(dateSplit[1] == 'October')
        {
            return dateSplit[0] + ' de octubre de ' + dateSplit[2];
        }
        else if(dateSplit[1] == 'November')
        {
            return dateSplit[0] + ' noviembre ' + dateSplit[2];
        }
        else if(dateSplit[1] == 'December')
        {
            return dateSplit[0] + ' diciembre ' + dateSplit[2];
        }
        return dateT;
    }

    public String getEnglishCreatedDate()
    {
        DateTime dt = opportunity.CreatedDate;
        String dateT = dt.format('dd MMMM YYYY');
        return dateT;
    }

    public class ProductWrapper
    {
        public OpportunityLineItem oli {get;set;}
        public Product2 product {get;set;}
        
        public ProductWrapper(OpportunityLineItem oli, Product2 product)
        {
            this.oli = oli;
            this.product = product;
        }
    }
    
    public  PageReference enviarAprobacion(){
        
        List<OpportunityLineItem> oliList = new List<OpportunityLineItem>();
        List<Opportunity_Package__c> oliPackageList = new List<Opportunity_Package__c>();
        Integer j=0;
        for(ProductWrapper w : oliServiceList)
        {
            if(!w.oli.Not_For_Create__c) 
            {
                w.oli.Category__c = 'SERVICIOS HOSPITALARIOS';
                w.oli.Location_Index_Product__c = j;
                w.oli.Descripcion_Custom__c = w.product.Name;
                j++;
                oliList.add(w.oli);
            }
            else
            {
                Opportunity_Package__c op = new Opportunity_Package__c();
                op.IVA__c = w.oli.IVA__c;
                op.Not_For_Create__c = false;
                op.Descripcion_Custom__c = w.product.Name;
                op.Opportunity__c = w.oli.OpportunityId;
                op.Name = w.product.Name;
                op.Original_Amount__c = w.oli.Original_Amount__c;
                op.Quantity__c = w.oli.Quantity;
                op.Total_Price__c = w.oli.TotalPrice;                        
                op.Category__c = 'SERVICIOS HOSPITALARIOS';
                op.ProductCode__c = w.product.ProductCode;
                op.Location_Index_Package__c = j;
                j++;
                oliPackageList.add(op);
            }
        }
        
        for(ProductWrapper w : oliServiceOtrosList)
        {
            if(!w.oli.Not_For_Create__c)
            {
                w.oli.Category__c = 'OTROS SERVICIOS';
                w.oli.Location_Index_Product__c = j;
                w.oli.Descripcion_Custom__c = w.product.Name;
                j++;
                oliList.add(w.oli);
            }
            else
            {
                Opportunity_Package__c op = new Opportunity_Package__c();
                op.IVA__c = w.oli.IVA__c;
                op.Not_For_Create__c = false;
                op.Opportunity__c = w.oli.OpportunityId;
                op.Name = w.product.Name;
                op.Descripcion_Custom__c = w.product.Name;
                op.Original_Amount__c = w.oli.Original_Amount__c;
                op.Quantity__c = w.oli.Quantity;
                op.Total_Price__c = w.oli.TotalPrice;                        
                op.Category__c = 'OTROS SERVICIOS';
                op.ProductCode__c = w.product.ProductCode;
                op.Otros__c = true;
                op.Location_Index_Package__c = j;
                j++;
                oliPackageList.add(op);
            } 
        }
        
        for(ProductWrapper w : oliInsumosList)
        {
            if(!w.oli.Not_For_Create__c)
            {
                w.oli.Category__c = 'INSUMOS HOSPITALARIOS';
                w.oli.Location_Index_Product__c = j;
                w.oli.Descripcion_Custom__c = w.product.Name;
                j++;
                oliList.add(w.oli);
            }
            else
            {
                Opportunity_Package__c op = new Opportunity_Package__c();
                op.IVA__c = w.oli.IVA__c;
                op.Not_For_Create__c = false;
                op.Descripcion_Custom__c = w.product.Name;
                op.Opportunity__c = w.oli.OpportunityId;
                op.Name = w.product.Name;
                op.Original_Amount__c = w.oli.Original_Amount__c;
                op.Quantity__c = w.oli.Quantity;
                op.Total_Price__c = w.oli.TotalPrice;                        
                op.Category__c = 'INSUMOS HOSPITALARIOS';
                op.ProductCode__c = w.product.ProductCode;
                op.Location_Index_Package__c = j;
                j++;
                oliPackageList.add(op);
            }
        }
        
        for(ProductWrapper w : oliInsumosOtrosList)
        {
            if(!w.oli.Not_For_Create__c)
            {
                w.oli.Category__c = 'OTROS INSUMOS';
                w.oli.Location_Index_Product__c = j;
                w.oli.Descripcion_Custom__c = w.product.Name;
                j++;
                oliList.add(w.oli);
            }
            else
            {
                Opportunity_Package__c op = new Opportunity_Package__c();
                op.IVA__c = w.oli.IVA__c;
                op.Not_For_Create__c = false;
                op.Descripcion_Custom__c = w.product.Name;
                op.Opportunity__c = w.oli.OpportunityId;
                op.Name = w.product.Name;
                op.Original_Amount__c = w.oli.Original_Amount__c;
                op.Quantity__c = w.oli.Quantity;
                op.Total_Price__c = w.oli.TotalPrice;                        
                op.Category__c = 'OTROS INSUMOS';
                op.ProductCode__c = w.product.ProductCode;
                op.Otros__c = true;
                op.Location_Index_Package__c = j;
                j++;
                oliPackageList.add(op);
            } 
        }
        
        for(ProductWrapper w : oliMedicamentosList)
        {
            if(!w.oli.Not_For_Create__c) 
            {
                w.oli.Category__c = 'MEDICAMENTOS HOSPITALARIOS';
                w.oli.Location_Index_Product__c = j;
                w.oli.Descripcion_Custom__c = w.product.Name;
                j++;
                oliList.add(w.oli);
            }
            else
            {
                Opportunity_Package__c op = new Opportunity_Package__c();
                op.IVA__c = w.oli.IVA__c;
                op.Not_For_Create__c = false;
                op.Opportunity__c = w.oli.OpportunityId;
                op.Name = w.product.Name;
                op.Descripcion_Custom__c = w.product.Name;
                op.Original_Amount__c = w.oli.Original_Amount__c;
                op.Quantity__c = w.oli.Quantity;
                op.Total_Price__c = w.oli.TotalPrice;                        
                op.Category__c = 'MEDICAMENTOS HOSPITALARIOS';
                op.ProductCode__c = w.product.ProductCode;
                op.Location_Index_Package__c = j;
                j++;
                oliPackageList.add(op);
            }
        }

        for(ProductWrapper w : oliHonorariosList)
        {
            if(!w.oli.Not_For_Create__c) 
            {
                w.oli.Category__c = 'HONORARIOS HOSPITALARIOS';
                w.oli.Location_Index_Product__c = j;
                w.oli.Descripcion_Custom__c = w.product.Name;
                j++;
                oliList.add(w.oli);
            }
            else
            {
               
                Opportunity_Package__c op = new Opportunity_Package__c();
                op.IVA__c = w.oli.IVA__c;
                op.Descripcion_Custom__c =w.product.Name;
                op.Not_For_Create__c = false;
                op.Opportunity__c = w.oli.OpportunityId;
                op.Name = w.product.Name;
                op.Original_Amount__c = w.oli.Original_Amount__c;
                op.Quantity__c = w.oli.Quantity;
                op.Total_Price__c = w.oli.TotalPrice;                        
                op.Category__c = 'HONORARIOS HOSPITALARIOS';
                op.ProductCode__c = w.product.ProductCode;
                op.Location_Index_Package__c = j;
                j++;
                oliPackageList.add(op);
            } 
        }
        
        for(ProductWrapper w : oliMedicamentosOtrosList)
        {
            if(!w.oli.Not_For_Create__c)
            {
                w.oli.Category__c = 'OTROS MEDICAMENTOS';
                w.oli.Location_Index_Product__c = j;
                w.oli.Descripcion_Custom__c = w.product.Name;
                j++;
                oliList.add(w.oli);
            }
            else
            {
                Opportunity_Package__c op = new Opportunity_Package__c();
                op.IVA__c = w.oli.IVA__c;
                op.Not_For_Create__c = false;
                op.Opportunity__c = w.oli.OpportunityId;
                op.Name = w.product.Name;
                op.Descripcion_Custom__c = w.product.Name;
                op.Original_Amount__c = w.oli.Original_Amount__c;
                op.Quantity__c = w.oli.Quantity;
                op.Total_Price__c = w.oli.TotalPrice;                        
                op.Category__c = 'OTROS MEDICAMENTOS';
                op.Otros__c = true;
                op.ProductCode__c = w.product.ProductCode;
                op.Location_Index_Package__c = j;
                j++;
                oliPackageList.add(op);
            } 
        }
        
        for(ProductWrapper w : oliExternalList)
        {
            if(!w.oli.Not_For_Create__c) 
            {
                w.oli.Category__c = 'EXTERNAL HOSPITALARIOS';
                w.oli.Location_Index_Product__c = j;
                w.oli.Descripcion_Custom__c = w.product.Name;
                j++;
                oliList.add(w.oli);
            }
            else
            {
                Opportunity_Package__c op = new Opportunity_Package__c();
                op.IVA__c = w.oli.IVA__c;
                op.Not_For_Create__c = false;
                op.Opportunity__c = w.oli.OpportunityId;
                op.Name = w.product.Name;
                op.Descripcion_Custom__c = w.product.Name;
                op.Original_Amount__c = w.oli.Original_Amount__c;
                op.Quantity__c = w.oli.Quantity;
                op.Total_Price__c = w.oli.TotalPrice;                        
                op.Category__c = 'EXTERNAL HOSPITALARIOS';
                op.ProductCode__c = w.product.ProductCode;
                op.Location_Index_Package__c = j;
                j++;
                oliPackageList.add(op);
            }
        }

        for(ProductWrapper w : oliHonorariosOtrosList)
        {
            if(!w.oli.Not_For_Create__c)
            {
                w.oli.Category__c = 'OTROS HONORARIOS';
                w.oli.Location_Index_Product__c = j;
                w.oli.Descripcion_Custom__c = w.product.Name;
                j++;
                oliList.add(w.oli);
            }
            else
            {
                Opportunity_Package__c op = new Opportunity_Package__c();
                op.IVA__c = w.oli.IVA__c;
                op.Not_For_Create__c = false;
                op.Opportunity__c = w.oli.OpportunityId;
                op.Name = w.product.Name;
                op.Descripcion_Custom__c =' w.product.Name';
                op.Original_Amount__c = w.oli.Original_Amount__c;
                op.Quantity__c = w.oli.Quantity;
                op.Total_Price__c = w.oli.TotalPrice;                        
                op.Category__c = 'OTROS HONORARIOS';
                op.Otros__c = true;
                op.ProductCode__c = w.product.ProductCode;
                op.Location_Index_Package__c = j;
                j++;
                oliPackageList.add(op);
            } 
        }

        for(ProductWrapper w : oliOtrosList)
        {
            if(!w.oli.Not_For_Create__c)
            {
                w.oli.Category__c = 'OTROS';
                w.oli.Location_Index_Product__c = j;
                j++;
                oliList.add(w.oli);
            }
            else
            {
                Opportunity_Package__c op = new Opportunity_Package__c();
                op.IVA__c = w.oli.IVA__c;
                op.Not_For_Create__c = false;
                op.Opportunity__c = w.oli.OpportunityId;
                op.Name = w.product.Name;
                op.Descripcion_Custom__c = w.product.Name;
                op.Original_Amount__c = w.oli.Original_Amount__c;
                op.Quantity__c = w.oli.Quantity;
                op.Total_Price__c = w.oli.TotalPrice;                        
                op.Category__c = 'OTROS';
                op.ProductCode__c = w.product.ProductCode;
                op.Location_Index_Package__c = j;
                j++;
                oliPackageList.add(op);
            }
        }
        
        try
        {
            upsert oliList;
            update account;
            opportunity.Approved_Discount__c = false;
            opportunity.Impuesto_al_Valor_Agregado_IVA__c = montoTotalIVAOpp;
            opportunity.PrecioAproximado_de_la_Cirugia__c = montoTotalOpp;
            opportunity.Precio_aproximado_de_la_Cirugia_con_Desc__c	 = montoTotalOppConDescuento;
            opportunity.Precio_aproximado_de_la_Cirugia_IVA__c = montoFinalOpp;
            if(nivelDescuento != null && nivelDescuento.contains('~~~~~'))
                opportunity.Nivel_de_descuento__c = nivelDescuento.split('~~~~~')[0];
            
            update opportunity;
            
            if(oliDeleteList.size() > 0)
                delete oliDeleteList;
            
            delete [select id from Opportunity_Package__c where Opportunity__c =: opportunity.Id];
            insert oliPackageList;
             Opportunity opp = [Select id,OwnerId from Opportunity where id=: opportunityId];
            User us  = [SELECT Id,ManagerId FROM User WHERE id =: opp.OwnerId];
            List<User> us2 = [SELECT Id,ManagerId FROM User WHERE email ='steven.vargas@metropolitanocr.com'];
            if(us.ManagerId != null){
        		Approval.ProcessSubmitRequest req1 = new Approval.ProcessSubmitRequest();
        		req1.setComments('Se ha generado una solicitud de aprobación');
        		req1.setObjectId(opp.Id);
                req1.setSubmitterId(us.ManagerId);
                List<Id> ids = new List<Id>();
                ids.add(us.ManagerId);
                req1.setNextApproverIds(ids);
                Approval.ProcessResult result = Approval.process(req1);
               
            }else{
              if(us2.size() > 0){
                Approval.ProcessSubmitRequest req1 = new Approval.ProcessSubmitRequest();
        		req1.setComments('Se ha generado una solicitud de aprobación');
        		req1.setObjectId(opp.Id);
               	req1.setSubmitterId(us2[0].Id);
                   List<Id> ids = new List<Id>();
                	ids.add(us2[0].Id);
                	req1.setNextApproverIds(ids);
                 Approval.ProcessResult result = Approval.process(req1);
                saveProducts();
              }else{
                 ApexPages.addMessage(new ApexPages.message(Apexpages.Severity.ERROR, 'Error al enviar a aprobación')); 
              }  
            }
            return new PageReference('/'+opportunityId).setRedirect(true);
        }
        catch(Exception ex)
        {   
            ApexPages.addMessage(new ApexPages.message(Apexpages.Severity.ERROR, ex.getMessage()));
            
            return null;
        }
        
           
           
       
        
    }

}