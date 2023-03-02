package de.lukegoll.application.textextractor;

import java.io.File;
import java.io.IOException;

public interface TextExtractor {
    public Object extractText(File file) throws IOException;
}
