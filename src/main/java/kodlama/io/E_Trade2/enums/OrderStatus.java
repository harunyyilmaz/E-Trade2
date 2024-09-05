package kodlama.io.E_Trade2.enums;

public enum OrderStatus {

    CREATED,
    PROCESSING,
    SHIPPED,
    DELIVERED,
    CANCELED,
    PENDING
}
/*
CREATED: Siparişin oluşturulduğu durum. Sipariş henüz işlenmeye başlamamış olabilir.

PROCESSING: Siparişin işleme alındığı ve muhtemelen hazırlanmakta olduğu durum.

SHIPPED: Siparişin gönderildiği ve kargoya verildiği durum.

DELIVERED: Siparişin alıcıya teslim edildiği durum.

CANCELED: Siparişin iptal edildiği durum.

PENDING : Enum içinde genellikle işlemin veya olayın beklemede olduğunu ifade eden bir durumdur.

 */