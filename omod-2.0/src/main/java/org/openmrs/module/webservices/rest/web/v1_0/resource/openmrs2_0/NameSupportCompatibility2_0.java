/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.webservices.rest.web.v1_0.resource.openmrs2_0;

import java.lang.reflect.Method;

import org.openmrs.PersonName;
import org.openmrs.annotation.OpenmrsProfile;
import org.openmrs.layout.name.NameSupport;
import org.openmrs.layout.name.NameTemplate;
import org.openmrs.module.webservices.rest.web.resource.api.NameSupportCompatibility;
import org.springframework.stereotype.Component;

@Component("webservices.rest.NameSupportCompatibility2_0")
@OpenmrsProfile(openmrsPlatformVersion = "2.0.*")
public class NameSupportCompatibility2_0 implements NameSupportCompatibility {

	@Override
	public String getDisplayString(PersonName personName) {
		
		try {
            NameTemplate nameTemplate = NameSupport.getInstance().getDefaultLayoutTemplate();

            if (nameTemplate!= null) {
                // need to use reflection since the format method was not added until later versions of openmrs
                Method format = NameTemplate.class.getDeclaredMethod("format", PersonName.class);
                return (String) format.invoke(nameTemplate, personName);
            }
        }
        catch (Exception e) {
            // fall through to just returning full name if no format method found or format fails
        }

        // otherwise, just return full name
        return personName.getFullName();
	}
}