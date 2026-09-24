# Session 17 Bài 3 — @CacheEvict & Cache-Aside

## Vì sao @CacheEvict, không @CachePut?

`@CachePut` ghi đè cache **ngay trong cùng request update**. Trong hệ thống nhiều instance / đọc song song dễ **Race Condition**:

1. Request A đọc cache cũ (hoặc DB cũ) gần lúc B update.
2. B `@CachePut` giá mới.
3. A vẫn có thể put lại giá cũ lên cache → **stale**.

**Cache-Aside + `@CacheEvict`:**
1. Ghi **DB trước** (source of truth).
2. **Xóa** cache key.
3. Lần GET sau → miss → đọc DB mới → nạp cache.

Không cố “đoán” giá trị cache trong lúc có concurrent read/write.

## API

```http
GET  http://localhost:8085/menu/1
PUT  http://localhost:8085/menu
Content-Type: application/json

{ "id": 1, "dish_name": "Phở Bò Đặc Biệt", "price": 75000 }
```

## Redis CLI

```bash
# Sau GET lần đầu
redis-cli GET menuCache::1

# Sau PUT update
redis-cli GET menuCache::1   # (nil) — đã evict

# GET lại → nạp giá 75000
redis-cli GET menuCache::1
```
