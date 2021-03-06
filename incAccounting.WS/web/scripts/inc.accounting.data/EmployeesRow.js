////////////////////////////////////////////////////////////////////////////////
// 
// WARNING - DO NOT EDIT THIS FILE
//
// These data objects are generated from the database.  If you need to add 
// properties, please update the database, then re-generate.
//
////////////////////////////////////////////////////////////////////////////////
if(!inc)
	var inc = {};
if(!inc.accounting)
	inc.accounting = {};
if(!inc.accounting.data)
	inc.accounting.data = {};

new function() {
	inc.accounting.data.EmployeesRow = function() {
		var obj = new inc.data.Row();

				
		/*
		 * @type java.lang.String
		 */
		obj.getGuid = function() { return obj.get("GUID"); };
		/*
		 * @param java.lang.String value 
		 */
		obj.setGuid = function(value) { obj.set("GUID", value); };
		
				
		/*
		 * @type java.lang.String
		 */
		obj.getFirstName = function() { return obj.get("First Name"); };
		/*
		 * @param java.lang.String value 
		 */
		obj.setFirstName = function(value) { obj.set("First Name", value); };
		
				
		/*
		 * @type java.lang.String
		 */
		obj.getLastName = function() { return obj.get("Last Name"); };
		/*
		 * @param java.lang.String value 
		 */
		obj.setLastName = function(value) { obj.set("Last Name", value); };
		
				
		/*
		 * @type java.lang.String
		 */
		obj.getSuffix = function() { return obj.get("Suffix"); };
		/*
		 * @param java.lang.String value 
		 */
		obj.setSuffix = function(value) { obj.set("Suffix", value); };
		
				
		/*
		 * @type java.lang.String
		 */
		obj.getTaxId = function() { return obj.get("Tax ID"); };
		/*
		 * @param java.lang.String value 
		 */
		obj.setTaxId = function(value) { obj.set("Tax ID", value); };
		
				
		/*
		 * @type java.util.Date
		 */
		obj.getDateOfBirth = function() { return obj.get("Date of Birth"); };
		/*
		 * @param java.util.Date value 
		 */
		obj.setDateOfBirth = function(value) { obj.set("Date of Birth", value); };
		
				
		/*
		 * @type java.lang.Boolean
		 */
		obj.getIsMale = function() { return obj.get("Is Male"); };
		/*
		 * @param java.lang.Boolean value 
		 */
		obj.setIsMale = function(value) { obj.set("Is Male", value); };
		
				
		/*
		 * @type java.lang.String
		 */
		obj.getEthnicity = function() { return obj.get("Ethnicity"); };
		/*
		 * @param java.lang.String value 
		 */
		obj.setEthnicity = function(value) { obj.set("Ethnicity", value); };
		
				
		/*
		 * @type java.lang.String
		 */
		obj.getJobTitle = function() { return obj.get("Job Title"); };
		/*
		 * @param java.lang.String value 
		 */
		obj.setJobTitle = function(value) { obj.set("Job Title", value); };
		
				
		/*
		 * @type java.lang.String
		 */
		obj.getSupervisorContactsGuid = function() { return obj.get("Supervisor Contacts GUID"); };
		/*
		 * @param java.lang.String value 
		 */
		obj.setSupervisorContactsGuid = function(value) { obj.set("Supervisor Contacts GUID", value); };
		
				
		/*
		 * @type java.util.Date
		 */
		obj.getDateHirred = function() { return obj.get("Date Hirred"); };
		/*
		 * @param java.util.Date value 
		 */
		obj.setDateHirred = function(value) { obj.set("Date Hirred", value); };
		
				
		/*
		 * @type java.util.Date
		 */
		obj.getDateTerminated = function() { return obj.get("Date Terminated"); };
		/*
		 * @param java.util.Date value 
		 */
		obj.setDateTerminated = function(value) { obj.set("Date Terminated", value); };
		
				
		/*
		 * @type java.util.Date
		 */
		obj.getDateVerified = function() { return obj.get("Date Verified"); };
		/*
		 * @param java.util.Date value 
		 */
		obj.setDateVerified = function(value) { obj.set("Date Verified", value); };
		
				
		/*
		 * @type java.lang.Boolean
		 */
		obj.getInsurance = function() { return obj.get("Insurance"); };
		/*
		 * @param java.lang.Boolean value 
		 */
		obj.setInsurance = function(value) { obj.set("Insurance", value); };
		
				
		/*
		 * @type java.lang.Boolean
		 */
		obj.getVacation = function() { return obj.get("Vacation"); };
		/*
		 * @param java.lang.Boolean value 
		 */
		obj.setVacation = function(value) { obj.set("Vacation", value); };
		
		

		return obj;
	};
};