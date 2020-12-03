export const cropImage = (url: string): string => {
    const upload = "/upload/";
    const index = url.indexOf(upload);
    return url.substring(0, index + upload.length) + "w_300,h_300,c_fill/" + url.substring(index + upload.length);
};