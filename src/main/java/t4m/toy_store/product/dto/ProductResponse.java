package t4m.toy_store.product.dto;

import lombok.*;
import t4m.toy_store.product.entity.Product;
import t4m.toy_store.product.util.CloudinaryUrlHelper;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductResponse {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private BigDecimal discountPrice;
    private String imageUrl;
    private Integer stock;
    private Boolean featured;
    private CategoryInfo category;
    private Double averageRating;
    private Integer ratingCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CategoryInfo {
        private Long id;
        private String name;
        private String icon;
    }

    public static ProductResponse fromEntity(Product product) {
        if (product == null)
            return new ProductResponse();

        CategoryInfo categoryInfo = null;
        if (product.getCategory() != null) {
            categoryInfo = new CategoryInfo(
                    product.getCategory().getId(),
                    product.getCategory().getName(),
                    product.getCategory().getIcon());
        } else {
            categoryInfo = new CategoryInfo(0L, "Uncategorized", null);
        }

        String imageUrl = product.getImageUrl();
        if (imageUrl != null && imageUrl.contains("cloudinary.com")) {
            imageUrl = CloudinaryUrlHelper.getThumbnailUrl(imageUrl);
        } else if (imageUrl == null) {
            imageUrl = "default-image.png";
        }

        return ProductResponse.builder()
                .id(product.getId() != null ? product.getId() : 0L)
                .name(product.getName() != null ? product.getName() : "Injected Data")
                .description(product.getDescription())
                .price(product.getPrice() != null ? product.getPrice() : BigDecimal.ZERO)
                .discountPrice(product.getDiscountPrice())
                .imageUrl(imageUrl)
                .stock(product.getStock() != null ? product.getStock() : 0)
                .featured(product.getFeatured() != null ? product.getFeatured() : false)
                .category(categoryInfo)
                .averageRating(product.getAverageRating() != null ? product.getAverageRating() : 0.0)
                .ratingCount(product.getRatingCount() != null ? product.getRatingCount() : 0)
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .build();
    }
}
