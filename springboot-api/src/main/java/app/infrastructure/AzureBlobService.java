package app.infrastructure;

import com.azure.storage.blob.*;
import com.azure.storage.blob.models.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
@RequiredArgsConstructor
public class AzureBlobService {

    private final BlobContainerClient blobContainerClient;

    public InputStream getProfileJson(String blobName) {
        BlobClient blobClient = blobContainerClient.getBlobClient(blobName);
        return blobClient.openInputStream();
    }
}