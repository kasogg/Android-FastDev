package com.kasogg.booster.util.http.builder;

import com.kasogg.booster.util.http.data.FileInput;
import com.kasogg.booster.util.http.request.OKHttpRequestCall;
import com.kasogg.booster.util.http.request.PostFormCall;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: KasoGG
 * Date:   2016-01-18 14:17
 */
public class PostFormBuilder extends OKHttpRequestCallBuilder<PostFormBuilder> {
    private List<FileInput> files = new ArrayList<>();

    @Override
    public OKHttpRequestCall build() {
        return new PostFormCall(url, tag, params, headers, files);
    }

    public PostFormBuilder addFile(String name, String filename, File file) {
        files.add(new FileInput(name, filename, file));
        return this;
    }

    public PostFormBuilder files(List<FileInput> files) {
        this.files = files;
        return this;
    }
}
