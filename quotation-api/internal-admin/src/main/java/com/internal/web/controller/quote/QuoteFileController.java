package com.internal.web.controller.quote;

import com.internal.common.annotation.Log;
import com.internal.common.core.controller.BaseController;
import com.internal.common.enums.BusinessType;
import com.internal.common.exception.CustomizedException;
import com.internal.common.exception.ServiceException;
import com.internal.common.utils.file.FileUtils;
import com.internal.common.utils.poi.EasyExcelUtil;
import com.internal.quote.service.IQuoteOpportunitiesService;
import com.internal.quote.vo.QuoteOpportunitiesCustomizableImportVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.LinkedList;
import java.util.List;

/**
 * 报价系统-文件相关Controller
 * 
 * @author internal
 * @date 2025-01-07
 */
@RestController
@AllArgsConstructor
@Tag(name = "报价系统-文件相关", description  = "报价系统-文件相关")
@RequestMapping("/quote/file")
public class QuoteFileController extends BaseController
{
    private final IQuoteOpportunitiesService quoteOpportunitiesService;

    /**
     * 下载功能清单模版
     */
    @Operation(summary = "下载功能清单模版", description  = "下载功能清单模版")
    @Log(title = "下载功能清单模版", businessType = BusinessType.EXPORT)
    @PostMapping("/customizableTemplate")
    public void downloadCustomizableTemplate(HttpServletResponse response) throws Exception {
        EasyExcelUtil<Object> util = new EasyExcelUtil<>(Object.class);
        String templatePath = "template/Cost_Customizable_template.xlsx";
        EasyExcelUtil.initResponse(response, "功能清单模版");

        util.exportEasyExcel(response, "sheet1", templatePath, null);
    }

    /**
     * 上传功能清单
     */
    @Operation(summary = "上传功能清单", description  = "上传功能清单")
    @Log(title = "上传功能清单")
    @PostMapping("/uploadCustomizable")
    public List<QuoteOpportunitiesCustomizableImportVO> uploadCustomizableExcel(@RequestParam("file") MultipartFile file) throws Exception
    {
        String ext = FileUtils.getFileExtension(file.getOriginalFilename());
        if(!"xlsx".equals(ext)){
            throw new CustomizedException("暂不支持改格式，请下载模版后填写重试");
        }
        EasyExcelUtil<QuoteOpportunitiesCustomizableImportVO> util = new EasyExcelUtil<>(QuoteOpportunitiesCustomizableImportVO.class);
        List<QuoteOpportunitiesCustomizableImportVO> result = new LinkedList<>();
        try {
            Boolean v = util.verifyProperties(file.getInputStream());
            if(!v){
                throw new CustomizedException("暂不支持改格式，请下载模版后填写重试");
            }
            result = util.importEasyExcel(file.getInputStream());
        } catch (Exception e) {
            throw new ServiceException("上传失败");
        }
        return result;
    }


}
