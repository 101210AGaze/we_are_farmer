import ddddocr
import sys

# def get_captcha(path):
#     ocr = ddddocr.DdddOcr()
#     with open(path, "rb") as f:
#         img_bytes = f.read()
#     res = ocr.classification(img_bytes)
#     return res



if __name__ == "__main__":
        path = sys.argv[1]
        ocr = ddddocr.DdddOcr()
        with open(path, "rb") as f:
            img_bytes = f.read()
        res = ocr.classification(img_bytes)
        print(res)


