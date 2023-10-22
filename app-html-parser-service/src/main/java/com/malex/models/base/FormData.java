package com.malex.models.base;

import lombok.Builder;
import lombok.Getter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Builder
@Getter
public class FormData {

    private MultiValueMap<String, String> formData;

    public FormData() {
        this.formData = new LinkedMultiValueMap<>();
    }

    /**
     * FORM_URLENCODED_DATA:
     * "BillSearchModel.registrationNumber", "9672", //
     * "BillSearchModel.registrationNumberCompareOperation", "2", //
     * "BillSearchModel.session", "10", //
     * "BillSearchModel.registrationRangeStart", "", //
     * "BillSearchModel.registrationRangeEnd", "", //
     * "BillSearchModel.name", "", //
     * "BillSearchModel.detailView", "false" //
     */
    public static class FormDataBuilder {

        /* "BillSearchModel.registrationNumber" */
        public FormData.FormDataBuilder registrationNumber(String number) {
            this.formData.add("BillSearchModel.registrationNumber", number);
            return this;
        }

        /* "BillSearchModel.registrationNumberCompareOperation" */
        public FormData.FormDataBuilder registrationNumberCompareOperation(String operation) {
            this.formData.add("BillSearchModel.registrationNumberCompareOperation", operation);
            return this;
        }

        /* "BillSearchModel.session" */
        public FormData.FormDataBuilder session(String session) {
            this.formData.add("BillSearchModel.session", session);
            return this;
        }

        /* "BillSearchModel.registrationRangeStart" */
        public FormData.FormDataBuilder registrationRangeStart(String registrationRangeStart) {
            this.formData.add("BillSearchModel.registrationRangeStart", registrationRangeStart);
            return this;
        }

        /* "BillSearchModel.registrationRangeEnd" */
        public FormData.FormDataBuilder registrationRangeEnd(String registrationRangeEnd) {
            this.formData.add("BillSearchModel.registrationRangeEnd", registrationRangeEnd);
            return this;
        }

        /* "BillSearchModel.name" */
        public FormData.FormDataBuilder name(String name) {
            this.formData.add("BillSearchModel.name", name);
            return this;
        }

        /* "BillSearchModel.detailView" */
        public FormData.FormDataBuilder detailView(String detailView) {
            this.formData.add("BillSearchModel.detailView", detailView);
            return this;
        }
    }

}