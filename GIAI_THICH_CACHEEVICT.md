# Giải thích chọn @CacheEvict thay @CachePut

## Thứ tự đúng (Cache-Aside write)

1. `menuRepository.save(menu)` — DB cập nhật trước  
2. `@CacheEvict(value = "menuCache", key = "#menu.id")` — xóa cache  
3. Request đọc sau tự load DB mới vào cache  

## Race Condition với @CachePut

Hai request đồng thời (update + read) có thể khiến giá trị **cũ** bị ghi lại vào Redis sau khi update đã xong. Evict chỉ invalidation → lần đọc tiếp theo luôn lấy từ DB mới → an toàn hơn trong distributed cache.
